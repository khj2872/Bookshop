<template>
<div class="site-section">
    <div class="container">
        <div class="row mb-5">
            <div class="col-md-12">
                <div class="border p-4 rounded" role="alert">
                    Returning customer? <a href="#">Click here</a> to login
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 mb-5 mb-md-0">
                <h2 class="h3 mb-3 text-black">01 주문상품 목록</h2>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th class="product-thumbnail">Image</th>
                        <th class="product-name">도서 이름</th>
                        <th class="product-price">판매가</th>
                        <th class="product-quantity">수량</th>
                        <th class="product-total">합계</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="order in orderList" :key="order.key">
                        <td class="product-thumbnail">
                            <img :src="order.item.image" alt="Image" class="img-fluid">
                        </td>
                        <td>
                            <h2 class="h5 text-black">
                                <p style="font-size: 14px; color: black">{{order.item.name}}</p>
                            </h2>
                        </td>
                        <td>
                            {{order.price | currency}}원<br>
                            {{order.price * order.savingRate / 100 | currency}}원<b>P</b>
                        </td>
                        <td>
                            <div class="input-group mb-3" style="max-width: 120px;">
                                {{order.count}}
                            </div>
                        </td>
                        <td>{{order.price * order.count | currency}}원</td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div class="col-md-12 mb-5 mb-md-0">
                <table class="table table-bordered">
                    <thead>
                    <tr style="font-size:18px">
                        <th>결제 예정금액</th>
                        <th>적립예정</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr style="font-size:16px">
                        <td>
                            <strong>{{total | currency}}원</strong>
                        </td>
                        <td>
                            {{savingPoint | currency}}원<b>P</b>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div class="col-md-12 mb-5 mb-md-0">
                <h2 class="h3 mb-3 text-black">02 배송정보</h2>
                <div class="p-3 p-lg-5 border">
                    <div class="form-group row">
                        <div class="col-md-12">
                            <label class="text-black">받으시는 분<span class="text-danger">*</span></label>
                            <input type="text" class="form-control" v-bind="recipient">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-12">
                            <label class="text-black">휴대폰 번호<span class="text-danger">*</span></label>
                            <input type="text" class="form-control" v-bind="phone">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-6">
                            <label class="text-black">배송주소<span class="text-danger">*</span></label>
                            <input type="text" class="form-control" disabled v-model="result.zonecode">
                        </div>
                        <div class="col-md-6">
                            <label class="text-black"></label><br><br>
                            <button type="button" @click="searchAddress">주소검색</button>

                        </div>
                    </div>

                    <div class="form-group">
                        <input type="text" class="form-control" disabled v-model="result.address">
                    </div>

                    <div class="form-group">
                        <input type="text" class="form-control" v-bind="address_detail">
                    </div>

                    <div class="form-group" v-show="searchDiv">
                        <VueDaumPostcode @complete="result = $event; searchAddress()"/>
                    </div>
                </div>
            </div>

            <div class="col-md-12 mb-5 mb-md-0">
                <h2 class="h3 mb-3 text-black">03 할인</h2>
                <table class="table table-bordered">
                    <tr>
                        <td>
                            주문쿠폰
                        </td>
                        <td>
                            할인쿠폰
                        </td>
                    </tr>
                    <tr>
                        <td>
                            통합 포인트
                        </td>
                        <td>
                            <input type="text" v-model.number="usingPoint" @focusout="pointCheck" @keyup="pointCheck">원 | {{member.point | currency}} 원 |
                            <input type="checkbox" v-model="isUsingAll" @click="useAll">모두사용
                        </td>
                    </tr>
                </table>
            </div>

            <div class="col-md-12">
                <div class="row mb-5">
                    <div class="col-md-12">
                        <h2 class="h3 mb-3 text-black">04 결제정보</h2>
                        <div class="p-3 p-lg-5 border">
                            <table class="table table-bordered">
                                <thead>
                                <tr style="font-size:18px">
                                    <th>상품금액</th>
                                    <th>(+) 배송비</th>
                                    <th>(-) 할인금액</th>
                                    <th>(=) 최종 결제금액</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr style="font-size:18px">
                                    <td>
                                        <strong>{{total | currency}}원</strong>
                                    </td>
                                    <td>
                                        <strong>(+) 0원</strong>
                                    </td>
                                    <td>
                                        <strong>(-) {{usingPoint | currency}}원</strong>
                                    </td>
                                    <td>
                                        <strong>(=) <span style="color:red">{{(total - usingPoint) | currency}}원</span></strong>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <div class="form-group">
                                <button class="btn btn-primary btn-lg py-3 btn-block" @click="startOrder()">Place Order</button>
                            </div>

                        </div>
                    </div>
                </div>

            </div>
        </div>
        <!-- </form> -->
    </div>
</div>
</template>

<script>
import Constant from '../../store/Constant';
import VueDaumPostcode from "vue-daum-postcode/src/vue-daum-postcode";

export default {
    components: {VueDaumPostcode},

    data: () => {
        return {
            orderList: [],
            member: '',
            recipient: '',
            phone: '',
            zipcode: '',
            address: '',
            address_detail: '',
            result: '',
            searchDiv: false,
            usingPoint: 0,
            isUsingAll: false
        }
    },

    computed: {
        orderListId() {
            return this.$store.state.orderListId;
        },

        total() {
            let total = 0;
            this.orderList.forEach(order => {
                total += order.price * order.count;
            });
            return total;
        },

        savingPoint() {
            let totalPoint = 0;
            this.orderList.forEach(order => {
                totalPoint += order.price * order.count * order.savingRate / 100;
            });
            return totalPoint;
        }
    },

    created() {
        this.getOrderList();
    },

    filters: {
        currency: (value) => {
            if (!value) {
                return 0;
            } else {
                return value.toFixed(0).replace(/(\d)(?=(\d{3})+(?:\.\d+)?$)/g, "$1,");
            }
        }
    },

    methods: {
        getOrderList() {
            console.log(this.orderListId);
            this.$store.dispatch(Constant.GET_ORDER_LIST, this.orderListId)
                .then(response => {
                    console.log(response.data);
                    this.orderList = response.data.orderList;
                    this.member = response.data.member;
                })
                .catch(ex => {
                    console.log(ex);
                })
        },

        searchAddress() {
            this.searchDiv = !this.searchDiv;
        },

        useAll() {
            if(this.isUsingAll) this.usingPoint = 0;
             else this.usingPoint = this.member.point;
        },

        pointCheck(e) {
            if(e.target.value > this.member.point) e.target.value = this.member.point;
        },

        startOrder() {
            this.$store.dispatch(Constant.START_ORDER, {  })
                .then(response => {

                })
                .catch(ex => {
                    console.log(ex);
                })
            // this.IMP();
        },

        IMP() {
            this.$IMP().request_pay({
                pg: 'html5_inicis',
                pay_method: 'card',
                merchant_uid: 'merchant_' + new Date().getTime(),
                name: '주문명:결제테스트',
                amount: 100,
                buyer_email: 'iamport@siot.do',
                buyer_name: '구매자이름',
                buyer_tel: '010-1234-5678',
                buyer_addr: '서울특별시 강남구 삼성동',
                buyer_postcode: '123-456'
            }, (result_success) => {
                //성공할 때 실행 될 콜백 함수
                let msg = '결제가 완료되었습니다.';
                msg += '고유ID : ' + result_success.imp_uid;
                msg += '상점 거래ID : ' + result_success.merchant_uid;
                msg += '결제 금액 : ' + result_success.paid_amount;
                msg += '카드 승인번호 : ' + result_success.apply_num;
                alert(msg);
            }, (result_failure) => {
                //실패시 실행 될 콜백 함수
                let msg = '결제에 실패하였습니다.';
                msg += '에러내용 : ' + result_failure.error_msg;
                alert(msg);
            })
        }
    }
}
</script>

<style scoped>

</style>