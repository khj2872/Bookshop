<template>
<div class="site-section">
    <div class="container">
        <div class="row mb-5">
            <div class="site-blocks-table">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th></th>
                        <th class="product-thumbnail">Image</th>
                        <th class="product-name">도서 이름</th>
                        <th class="product-price">판매가</th>
                        <th class="product-quantity">수량</th>
                        <th class="product-total">합계</th>
                        <th class="product-remove">삭제</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="cart in cartList" :key="cart.key">
                        <td><input type="checkbox" :id="cart.id" :value="cart.id" v-model="check"></td>
                        <td class="product-thumbnail">
                            <router-link :to="'/itemDetail/'+cart.item.id">
                                <img :src="cart.item.image" alt="Image" class="img-fluid">
                            </router-link>
                        </td>
                        <td>
                            <h2 class="h5 text-black">
                                <router-link :to="'/itemDetail/'+cart.item.id">
                                    <p style="font-size: 14px; color: black">{{cart.item.name}}</p>
                                </router-link>
                            </h2>
                        </td>
                        <td>
                            {{cart.price | currency}}원<br>
                            {{cart.price * cart.savingRate / 100 | currency}}원<b>P</b>
                        </td>
                        <td>
                            <div class="input-group mb-3" style="max-width: 120px;">
                                <input type="number" min="1" max="999" class="form-control text-center"
                                       v-model.number="cart.count" aria-label="Example text with button addon" aria-describedby="button-addon1">
                                <button type="button" class="btn btn-default btn-xs" @click="updateCart(cart.id, cart.count)">수정</button>
                            </div>
                        </td>
                        <td>{{cart.price * cart.count | currency}}원</td>
                        <td><button type="button" @click="deleteCart(cart.id)" class="btn btn-danger btn-sm">X</button></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="row mb-5">
                    <div class="col-md-6 mb-3 mb-md-0">
                        <button class="btn btn-primary btn-sm btn-block">Update Cart</button>
                    </div>
                    <div class="col-md-6">
                        <button class="btn btn-outline-primary btn-sm btn-block">Continue Shopping</button>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <label class="text-black h4" for="coupon">Coupon</label>
                        <p>Enter your coupon code if you have one.</p>
                    </div>
                    <div class="col-md-8 mb-3 mb-md-0">
                        <input type="text" class="form-control py-3" id="coupon" placeholder="Coupon Code">
                    </div>
                    <div class="col-md-4">
                        <button class="btn btn-primary btn-sm">Apply Coupon</button>
                    </div>
                </div>
            </div>
            <div class="col-md-6 pl-5">
                <div class="row justify-content-end">
                    <div class="col-md-7">
                        <div class="row">
                            <div class="col-md-12 text-right border-bottom mb-5">
                                <h3 class="text-black h4 text-uppercase">상품 금액</h3>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <span class="text-black">적립예정</span>
                            </div>
                            <div class="col-md-6 text-right">
                                <strong class="text-black">{{savingPoint | currency}}원<b>P</b></strong>
                            </div>
                        </div>
                        <div class="row mb-5">
                            <div class="col-md-6">
                                <span class="text-black">결제예정금액</span>
                            </div>
                            <div class="col-md-6 text-right">
                                <strong class="text-black">{{total | currency}}원</strong>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <button type="button" @click="order()" class="btn btn-primary btn-lg py-3 btn-block" >Proceed To Checkout</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import Constant from '../../store/Constant';
export default {
    data: () => {
        return {
            cartList: [],
            check: []
        }
    },

    computed : {
        total() {
            let total = 0;
            this.cartList.forEach(cart => {
                this.check.forEach((cartId, index) => {
                    if(cart.id === this.check[index]) {
                        total += cart.price * cart.count;
                    }
                })
            });
            return total;
        },

        savingPoint() {
            let totalPoint = 0;
            this.cartList.forEach(cart => {
                this.check.forEach((cartId, index) => {
                    if(cart.id === this.check[index]) {
                        totalPoint += cart.price * cart.count * cart.savingRate / 100;
                    }
                })
            });
            return totalPoint;
        }
    },

    created() {
        this.getCartList();

    },

    filters: {
        currency: (value) => {
            if (!value) {
                return '';
            } else {
                return value.toFixed(0).replace(/(\d)(?=(\d{3})+(?:\.\d+)?$)/g, "$1,");
            }
        }
    },

    methods : {
        getCartList() {
            this.$store.dispatch(Constant.GET_CART_LIST)
                .then(response => {
                    console.log(response);
                    this.cartList = response.data;
                })
                .catch(ex => {
                    console.log(ex);
                })
        },

        deleteCart(cartId) {
            if(confirm('선택하신 상품을 삭제하시겠습니까?')) {
                this.$store.dispatch(Constant.DELETE_CART, cartId)
                    .then(response => {
                        alert('상품이 성공적으로 삭제되었습니다.');
                        this.cartList = response.data;
                    })
                    .catch(ex => {
                        console.log(ex);
                        alert('상품 삭제중 오류가 발생하였습니다.');
                    })
            }
        },

        updateCart(cartId, count) {
            if(confirm('수량을 변경하시겠습니까?')) {
                this.$store.dispatch(Constant.UPDATE_CART, { id : cartId, count : parseInt(count) })
                    .then(response => {
                        this.cartList = response.data;
                    })
                    .catch(ex => {
                        console.log(ex);
                        alert('수량 수정중 오류가 발생하였습니다.');
                    })
            }
        },

        order() {
            this.$store.commit(Constant.CART_ORDER_STEP_ONE, this.check);
            console.log('check : ' + this.check);
            this.$router.push('/order');
        }
    }
}
</script>

<style scoped>

</style>