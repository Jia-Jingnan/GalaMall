package com.lilith.galamall.service.impl;

import com.google.common.collect.Lists;
import com.lilith.galamall.common.Const;
import com.lilith.galamall.common.GalaRes;
import com.lilith.galamall.common.ResponseCode;
import com.lilith.galamall.dao.CartMapper;
import com.lilith.galamall.dao.ProductMapper;
import com.lilith.galamall.entity.Cart;
import com.lilith.galamall.entity.Product;
import com.lilith.galamall.service.CartService;
import com.lilith.galamall.util.BigDecimalUtil;
import com.lilith.galamall.util.PropertiesUtil;
import com.lilith.galamall.vo.CartProductVO;
import com.lilith.galamall.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author:JiaJingnan
 * @Date: 上午1:00 2021/7/21
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    public GalaRes<CartVO> add(Integer userId, Integer productId, Integer count){

        if (productId == null || count == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARRGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARRGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if (cart == null){
            // 这个产品不在购物车中，需要新增一个这个产品的记录
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
            cartMapper.insert(cartItem);
        } else {
            // 如果产品已经在购物车里
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }

        CartVO cartVO = this.getCartVOLimit(userId);

        return GalaRes.createBySuccess(cartVO);
    }

    //更新购物车方法
    public GalaRes<CartVO> update(Integer userId, Integer productId, Integer count){
        if (productId == null || count == null){
            return GalaRes.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARRGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARRGUMENT.getDesc());
        }

        Cart cart = cartMapper.selectCartByUserIdProductId(userId,productId);

        if (cart != null){
            cart.setQuantity(count);
        }
        // 更新购物车
        cartMapper.updateByPrimaryKeySelective(cart);

        CartVO cartVO = this.getCartVOLimit(userId);
        return GalaRes.createBySuccess(cartVO);
    }

    // 购物车核心方法
    private CartVO getCartVOLimit(Integer userId){
        CartVO cartVO = new CartVO();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVO> cartProductVOList = Lists.newArrayList();

        // 初始化购物车总价为0
        BigDecimal cartTotalPrice = new BigDecimal("0");

        // cartList做空判断
        if (CollectionUtils.isEmpty(cartList)){
            for (Cart cartItem : cartList){
                CartProductVO cartProductVO = new CartProductVO();
                cartProductVO.setId(cartItem.getId());
                cartProductVO.setUserId(cartItem.getUserId());
                cartProductVO.setProductId(cartItem.getProductId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getId());
                if (product != null){
                    cartProductVO.setProductMainImage(product.getMainImage());
                    cartProductVO.setProductName(product.getName());
                    cartProductVO.setProductSubtitle(product.getSubtitle());
                    cartProductVO.setProductStatus(product.getStatus());
                    cartProductVO.setProductPrice(product.getPrice());
                    cartProductVO.setProductStock(product.getStock());
                    // 判断库存
                    int buyLimitCount = 0;
                    if (product.getStock() >= cartItem.getQuantity()){
                        // 库存充足的时候
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVO.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        buyLimitCount = product.getStock();
                        cartProductVO.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        // 购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }

                    cartProductVO.setQuantity(buyLimitCount);

                    // 计算产品的总价
                    cartProductVO.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),
                            cartProductVO.getQuantity().doubleValue()));
                    cartProductVO.setProductChecked(cartItem.getChecked());

                }

                if (cartItem.getChecked() == Const.Cart.CHECKED){
                    // 如果已经勾选，增加到整个购物车总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),
                            cartProductVO.getProductTotalPrice().doubleValue());
                }

                cartProductVOList.add(cartProductVO);
            }
        }

        cartVO.setCartTotalPrice(cartTotalPrice);
        cartVO.setCartProductVOList(cartProductVOList);
        cartVO.setAllChecked(this.getAllCheckedStatus(userId));
        cartVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        return cartVO;

    }

    private Boolean getAllCheckedStatus(Integer userId){
        if (userId == null){
            return false;
        }

        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }
}
