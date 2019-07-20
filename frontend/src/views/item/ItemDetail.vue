<template>
<div>
    <div class="bg-light py-3">
        <div class="container">
            <div class="row" v-if="!loading">
                <div class="col-md-12 mb-0"><router-link to="/">Home</router-link> <span class="mx-2 mb-0">/</span>
                    <strong class="text-black">{{itemDetail.category.name}}</strong>
                </div>
            </div>
        </div>
    </div>

    <div class="site-section">
        <div class="container">
            <div class="row">
                <div class="col-md-6" style="text-align: center">
                    <img v-bind:src="itemDetail.image" style="height: 100%" alt="Image">
                </div>
                <div class="col-md-6">
                    <h2 class="text-black">{{itemDetail.name}}</h2>
                    <p style="font-size: 15px;"><strong>{{itemDetail.writer}}</strong> 지음 | <strong>{{itemDetail.publicationDate}}</strong> 출간</p>
<!--                    <p>{{itemDetail.detail}}</p>-->
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
                    <h2>책 소개</h2>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="nonloop-block-3 owl-carousel">
                        <div class="item">
                            <div class="block-4 text-center">
                                <div class="block-4-text p-4">
                                    <h3>Tank Top</h3>
                                    <p class="mb-0">Finding perfect t-shirt</p>
                                    <p class="text-primary font-weight-bold">$50</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-7 site-section-heading text-center pt-4">
                    <h2>리뷰</h2>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="nonloop-block-3 owl-carousel">
                        <div class="item">
                            <div class="block-4 text-center">
                                <div class="block-4-text p-4">
                                    <span>
                                        <strong>평점</strong>
                                    </span>
                                    <star-rating v-model="rating"
                                                 :star-size="40"
                                                 :rounded-corners="true"
                                                 :inline="true"
                                                 :show-rating="false"
                                    >
                                    </star-rating>
                                    <p></p>
                                    <textarea class="form-control" rows="3" v-model="review" placeholder="리뷰를 입력해주세요."></textarea>
                                    <p></p>
                                    <button type="button" @click="onSubmitReview" class="btn btn-primary btn-lg">리뷰 남기기</button>
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
import StarRating from 'vue-star-rating'
export default {
    data: () => {
        return {
            loading: false,
            itemid : '',
            itemDetail : '',
            count : 1,
            rating: '',
            review: ''

        }
    },

    created() {
        this.itemid = this.$route.params.itemid;
        this.getItemDetail(this.itemid);
    },

    components: {
        StarRating
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
            this.loading = true;
            this.$store.dispatch(Constant.GET_ITEM_DETAIL, itemid)
                .then(response => {
                    console.log(response);
                    this.itemDetail = response.data;
                    this.loading = false;
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
                this.$store.dispatch(Constant.ADD_CART, { itemId : this.itemDetail.id, count : Number(this.count) })
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
        },

        onSubmitReview() {
            console.log("rating : " + this.rating);
            console.log("review : " + this.review);
            const param = {
                item : {
                    id : this.itemDetail.id
                },
                rating : this.rating,
                content : this.review
            };
            this.$store.dispatch(Constant.ADD_REVIEW, param)
                .then(response => {
                    this.itemDetail.reviews = null;
                    this.itemDetail.reviews = response.data;
                    alert('리뷰가 등록되었습니다.');
                })
                .catch(ex => {

                })
        }

    }
}
</script>

<style scoped>

</style>