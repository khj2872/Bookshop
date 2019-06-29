<template>
<div>
    <div class="bg-light py-3">
        <div class="container">
            <div class="row">
                <div class="col-md-12 mb-0"><a href="index.html">Home</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">Tank Top T-Shirt</strong></div>
            </div>
        </div>
    </div>

    <div class="site-section">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <img v-bind:src="itemDetail.image" alt="Image" class="img-fluid">
                </div>
                <div class="col-md-6">
                    <h2 class="text-black">{{itemDetail.name}}</h2>
                    <p>{{itemDetail.detail}}</p>
                    <p><strong class="text-primary h4">{{itemDetail.price | currency}}원</strong></p>
                    <div class="mb-5">
                        <div class="input-group mb-3" style="max-width: 120px;">
                            <div class="input-group-prepend">
                                <button class="btn btn-outline-primary js-btn-minus" @click="minus(count)" type="button">&minus;</button>
                            </div>
                            <input type="text" class="form-control text-center" v-model.number="count" maxlength="3"
                                   aria-label="Example text with button addon" aria-describedby="button-addon1">
                            <div class="input-group-append">
                                <button class="btn btn-outline-primary js-btn-plus" @click="plus(count)" type="button">&plus;</button>
                            </div>
                        </div>

                    </div>
                    <button type="button" @click="addCart()" class="buy-now btn btn-sm btn-danger" style="margin-right: 10px">장바구니 담기</button>
                    <button type="button" @click="directOrder()" class="buy-now btn btn-sm btn-primary">바로구매</button>

                </div>
            </div>
        </div>
    </div>

    <div class="site-section block-3 site-blocks-2 bg-light">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-7 site-section-heading text-center pt-4">
                    <h2>Featured Products</h2>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="nonloop-block-3 owl-carousel">
                        <div class="item">
                            <div class="block-4 text-center">
                                <figure class="block-4-image">
                                    <img src="images/cloth_1.jpg" alt="Image placeholder" class="img-fluid">
                                </figure>
                                <div class="block-4-text p-4">
                                    <h3><a href="#">Tank Top</a></h3>
                                    <p class="mb-0">Finding perfect t-shirt</p>
                                    <p class="text-primary font-weight-bold">$50</p>
                                </div>
                            </div>
                        </div>
                        <div class="item">
                            <div class="block-4 text-center">
                                <figure class="block-4-image">
                                    <img src="images/shoe_1.jpg" alt="Image placeholder" class="img-fluid">
                                </figure>
                                <div class="block-4-text p-4">
                                    <h3><a href="#">Corater</a></h3>
                                    <p class="mb-0">Finding perfect products</p>
                                    <p class="text-primary font-weight-bold">$50</p>
                                </div>
                            </div>
                        </div>
                        <div class="item">
                            <div class="block-4 text-center">
                                <figure class="block-4-image">
                                    <img src="images/cloth_2.jpg" alt="Image placeholder" class="img-fluid">
                                </figure>
                                <div class="block-4-text p-4">
                                    <h3><a href="#">Polo Shirt</a></h3>
                                    <p class="mb-0">Finding perfect products</p>
                                    <p class="text-primary font-weight-bold">$50</p>
                                </div>
                            </div>
                        </div>
                        <div class="item">
                            <div class="block-4 text-center">
                                <figure class="block-4-image">
                                    <img src="images/cloth_3.jpg" alt="Image placeholder" class="img-fluid">
                                </figure>
                                <div class="block-4-text p-4">
                                    <h3><a href="#">T-Shirt Mockup</a></h3>
                                    <p class="mb-0">Finding perfect products</p>
                                    <p class="text-primary font-weight-bold">$50</p>
                                </div>
                            </div>
                        </div>
                        <div class="item">
                            <div class="block-4 text-center">
                                <figure class="block-4-image">
                                    <img src="images/shoe_1.jpg" alt="Image placeholder" class="img-fluid">
                                </figure>
                                <div class="block-4-text p-4">
                                    <h3><a href="#">Corater</a></h3>
                                    <p class="mb-0">Finding perfect products</p>
                                    <p class="text-primary font-weight-bold">$50</p>
                                </div>
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
            itemid : '',
            itemDetail : '',
            count : 1,

        }
    },

    created() {
        this.itemid = this.$route.params.itemid;
        this.getItemDetail(this.itemid);
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
        getItemDetail(itemid) {
            this.$store.dispatch(Constant.GET_ITEM_DETAIL, itemid)
                .then(response => {
                    console.log(response);
                    this.itemDetail = response.data;
                })
                .catch(ex => {
                    console.log(ex);
                })
        },

        minus(count) {
            let numCount = count;
            if(numCount > 1) numCount -= 1;
            this.count = numCount;
        },

        plus(count) {
            let numCount = parseInt(count);
            numCount += 1;
            this.count = numCount;
        },

        addCart() {
            if(this.$store.state.loggedIn) {
                this.countCheck();
                this.$store.dispatch(Constant.ADD_CART, { itemId : this.itemDetail.id, count : parseInt(this.count) })
                    .then(() => {
                        if(confirm('선택한 상품이 장바구니에 담겼습니다.\n장바구니로 이동하시겠습니까?')) {
                            this.$router.push('/cartList');
                        }
                    })
                    .catch(ex => {
                        console.log(ex);
                    })
            } else {
                alert('로그인이 필요합니다.');
                this.$router.push('/login');
            }
        },

        directOrder() {
            if(this.$store.state.loggedIn) {
                this.countCheck();
                this.$store.dispatch(Constant.ADD_CART, { itemId : this.itemDetail.id, count : parseInt(this.count) })
                    .then(response => {
                        this.$store.commit(Constant.DIRECT_ORDER_STEP_ONE, response.data);
                        this.$router.push('/order');
                    })
                    .catch(ex => {
                        console.log(ex);
                    });
            } else {
                alert('로그인이 필요합니다.');
                this.$router.push('/login');
            }
        },

        countCheck() {
            if(parseInt(this.count) < 1) {
                alert('주문수량은 1개 이상이어야 합니다. ');
                return false;
            }
        }

    }
}
</script>

<style scoped>

</style>