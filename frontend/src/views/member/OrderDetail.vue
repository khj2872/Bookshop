<template>
<div class="site-section">
    <div class="container">
        <div class="row">
            <div class="col-md-12 mb-5 mb-md-0">
                <h2 class="h3 mb-3 text-black">01 주문자 정보</h2>
                <table class="table table-bordered">
                    <colgroup>
                        <col width="25%">
                        <col width="75">
                    </colgroup>
                    <tr style="font-size:16px;">
                        <td style="text-align: center">주문하신 분</td>
                        <td>{{member.username}}</td>
                    </tr>
                    <tr style="font-size:16px;">
                        <td style="text-align: center">휴대폰 번호</td>
                        <td>{{member.phone}}</td>
                    </tr>
                    <tr style="font-size:16px;">
                        <td style="text-align: center">이메일</td>
                        <td>{{member.userEmail}}</td>
                    </tr>
                </table>
            </div>


            <div class="col-md-12 mb-5 mb-md-0">
                <h2 class="h3 mb-3 text-black">02 배송정보</h2>
                <table class="table table-bordered" style="margin:auto;">
                    <colgroup>
                        <col width="14%">
                        <col width="70">
                        <col>
                        <col width="5%">
                        <col width="14%">
                        <col width="10%">
                    </colgroup>
                    <tbody>
                    <tr>
                        <td>주문번호</td>
                        <td colspan="2">상품정보</td>
                        <td>수량</td>
                        <td>판매가 합계</td>
                        <td>주문상태</td>
                    </tr>
                    <tr>
                        <td :rowspan="orderDetail.orderItems.length">
                            <strong>{{orderDetail.id}}</strong>
                            <p></p>
                            ({{orderDetail.orderDate | formatDate}})
                        </td>

                        <td>
                            <img :src="orderDetail.orderItems[0].item.image" alt="Image" class="img-fluid">
                        </td>
                        <td>
                            <p style="font-size: 14px; color: black">{{orderDetail.orderItems[0].item.name}}</p>
                        </td>
                        <td>
                            {{orderDetail.orderItems[0].count}}
                        </td>
                        <td>
                            {{orderDetail.orderItems[0].price | currency}} 원
                        </td>
                        <td v-if="orderDetail.status === 'ORDER' && orderDetail.delivery.status ==='READY'">
                            준비중
                            <p></p>
                            <button type="button" @click="">배송조회</button>
                        </td>
                        <td v-if="orderDetail.status === 'ORDER' && orderDetail.delivery.status ==='COMP'">
                            배송중
                            <p></p>
                            <button type="button" @click="">배송조회</button>
                        </td>
                        <td v-if="orderDetail.status === 'CANCEL'">
                            주문취소
                            <p></p>
                            <button type="button" @click="">배송조회</button>
                        </td>
                    </tr>
                    <template v-for="(orderItem, index) in orderDetail.orderItems" v-if="index > 0">
                        <tr>
                            <td>
                                <img :src="orderItem.item.image" alt="Image" class="img-fluid">

                            </td>
                            <td>
                                <p style="font-size: 14px; color: black">{{orderItem.item.name}}</p>
                            </td>
                            <td>
                                {{orderItem.count}}
                            </td>
                            <td>
                                {{orderItem.price | currency}} 원
                            </td>
                            <td v-if="orderDetail.status === 'ORDER' && orderDetail.delivery.status ==='READY'">
                                준비중
                                <p></p>
                                <button type="button" @click="">배송조회</button>
                            </td>
                            <td v-if="orderDetail.status === 'ORDER' && orderDetail.delivery.status ==='COMP'">
                                배송중
                                <p></p>
                                <button type="button" @click="">배송조회</button>
                            </td>
                            <td v-if="orderDetail.status === 'CANCEL'">
                                주문취소
                                <p></p>
                                <button type="button" @click="">배송조회</button>
                            </td>
                        </tr>
                    </template>
                    </tbody>
                </table>
            </div>

            <div class="col-md-12 mb-5 mb-md-0">
                <h2 class="h3 mb-3 text-black"></h2>
                <table class="table table-bordered">
                    <colgroup>
                        <col width="25%">
                        <col width="75">
                    </colgroup>
                    <tr style="font-size:16px;">
                        <td style="text-align: center">받으실 분</td>
                        <td>{{orderDetail.delivery.address.receiver}}</td>
                    </tr>
                    <tr style="font-size:16px;">
                        <td style="text-align: center">휴대폰 번호</td>
                        <td>{{orderDetail.delivery.address.telPhone}}</td>
                    </tr>
                    <tr style="font-size:16px;">
                        <td style="text-align: center">주소</td>
                        <td>[{{orderDetail.delivery.address.zipcode}}] {{orderDetail.delivery.address.address}}</td>
                    </tr>
                </table>
            </div>

            <div class="col-md-12">
                <div class="row mb-5">
                    <div class="col-md-12">
                        <h2 class="h3 mb-3 text-black">03 결제정보</h2>
                        <div class="p-3 p-lg-5 border">
                            <table class="table table-bordered" style="text-align: center">
                                <thead>
                                <tr style="font-size:18px">
                                    <th>주문금액</th>
                                    <th>(-) 할인금액</th>
                                    <th>(=) 최종 결제금액</th>
                                    <th>적립 통합포인트</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr style="font-size:18px">
                                    <td>
                                        <strong>{{orderDetail.totalPrice | currency}}원</strong>
                                    </td>
                                    <td>
                                        <strong>(-) {{orderDetail.usingPoint | currency}}원</strong>
                                    </td>
                                    <td>
                                        <strong>(=) <span style="color:red">{{(orderDetail.totalPrice - orderDetail.usingPoint) | currency}}원</span></strong>
                                    </td>
                                    <td>
                                        <strong>{{orderDetail.savingPoint | currency}}원</strong>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <div class="form-group">
                                <router-link class="btn btn-primary btn-lg py-3 btn-block" to="/myPage">주문/배송조회 가기</router-link>
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
import moment from 'moment'
export default {

    mounted() {
        console.log(this.orderDetail);
    },

    filters: {
        currency(value) {
            if (!value) {
                return '';
            } else {
                return value.toFixed(0).replace(/(\d)(?=(\d{3})+(?:\.\d+)?$)/g, "$1,");
            }
        },

        formatDate(date) {
            if (date) {
                return moment(String(date)).format('YYYY-MM-DD')
            }
        }
    },


    computed: {
        orderDetail() {
            return this.$store.state.orderDetail;
        },

        member() {
            return this.$store.state.member;
        }
    }

}
</script>

<style scoped>

</style>