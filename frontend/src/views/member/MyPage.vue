<template>
    <div class="site-section">
        <div class="container">
            <div class="row">
                <div class="col-md-12 mb-5 mb-md-0">
                    <div class="row mb-5">
                        <table class="table table-bordered" style="margin:auto;">
                            <tr style="font-size:16px;">
                                <td style="text-align: center">
                                    상품명
                                </td>
                                <td>
                                    <input type="text" class="form-control" v-model="itemSearch" placeholder="상품명을 입력해주세요.">
                                </td>
                                <td rowspan="2" style="text-align: center; display: table-cell; vertical-align: inherit">
                                    <button type="button" class="btn btn-primary" @click="getOrderList">검색</button>
                                </td>
                            </tr>
                            <tr style="font-size:16px;">
                                <td style="text-align: center">
                                    주문상태
                                </td>
                                <td>
                                    <select v-model="orderStatus" class="form-control">
                                        <option value="">선택</option>
                                        <option value="ORDER">주문</option>
                                        <option value="CANCEL">취소</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </div>

                    <div class="row mb-5">
                        <table class="table table-bordered" style="margin:auto;">
                            <colgroup>
                                <col width="14%">
                                <col width="14%">
                                <col width="70">
                                <col>
                                <col width="5%">
                                <col width="15%">
                            </colgroup>
                            <tbody>
                            <tr>
                                <td>주문번호</td>
                                <td>주문금액</td>
                                <td colspan="2">상품정보</td>
                                <td>수량</td>
                                <td>주문상태</td>
                            </tr>
                            <tr v-for="order in orderList.orders" :key="order.id">
                                <td :rowspan="order.orderItems.length">
                                    <strong>{{order.id}}</strong>
                                    <p></p>
                                    ({{order.orderDate | formatDate}})
                                    <p></p>
                                    <button @click="getOrderDetail(order.id)">주문상세보기</button>
                                </td>
                                <td :rowspan="order.orderItems.length">
                                    {{order.totalPrice | currency}} 원
                                </td>
                                <td>
                                    <img :src="order.orderItems[0].item.image" alt="Image" class="img-fluid">
                                </td>
                                <td>
                                    <p style="font-size: 14px; color: black">{{order.orderItems[0].item.name}}</p>
                                </td>
                                <td>
                                    {{order.orderItems[0].count}}
                                </td>
                                <td v-if="order.status === 'ORDER' && order.delivery.status ==='READY'">
                                    준비중
                                </td>
                                <td v-if="order.status === 'ORDER' && order.delivery.status ==='COMP'">
                                    배송중
                                </td>
                                <td v-if="order.status === 'CANCEL'">
                                    주문취소
                                </td>
                            </tr>
                            <tr v-for="(orderItem, index) in orderList.orders.orderItems" v-if="index > 0">
                                <td>
                                    <img :src="orderItem.item.image" alt="Image" class="img-fluid">

                                </td>
                                <td>
                                    <p style="font-size: 14px; color: black">{{orderItem.item.name}}</p>
                                </td>
                                <td>
                                    {{orderItem.count}}
                                </td>
                                <td v-if="order.status === 'ORDER' && order.delivery.status ==='READY'">
                                    준비중
                                </td>
                                <td v-if="order.status === 'ORDER' && order.delivery.status ==='COMP'">
                                    배송중
                                </td>
                                <td v-if="order.status === 'CANCEL'">
                                    주문취소
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Constant from '../../store/Constant';
import moment from 'moment'

export default {
    data() {
        return {
            orderList: {
                page:0, size:Constant.ORDER_PAGE_SIZE, totalPage:0, totalItems: 0, orders:[]
            },
            member: '',
            itemSearch: '',
            orderStatus: ''
        }
    },

    created() {
        this.getOrderList();
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


    methods: {
        getOrderList() {
            const param = {
                itemName : this.itemSearch,
                orderStatus: this.orderStatus,
                page : this.orderList.page,
                size : this.orderList.size
            };
            this.$store.dispatch(Constant.GET_COMPLETE_ORDER_LIST, param)
                .then(response => {
                    console.log(response);
                    this.member = response.data.member;
                    this.orderList.orders = response.data.orders;
                })
                .catch(ex => {
                    console.log(ex);
                })
        },

        getOrderDetail(id) {
            console.log('faef');
            this.$store.dispatch(Constant.GET_ORDER_DETAIL, id)
                .then(() => {
                    console.log("ddd");
                    this.$router.push('/myPage/orderDetail');
                })
                .catch(ex => {
                    console.log(ex);
                })
        }


    }

}
</script>

<style scoped>

</style>