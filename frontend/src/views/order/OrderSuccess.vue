<template>
    <div style="text-align: center">
        <h2><strong>{{resultMsg}}</strong></h2>
        <table class="table table-bordered" style="text-align: center">
            <tr style="font-size:16px;">
                <td>
                    <strong>주문번호</strong>
                </td>
                <td>
                    <strong>{{orderId}}</strong>
                </td>
            </tr>
        </table>
    </div>
</template>

<script>
import Constant from '../../store/Constant';

export default {

    data() {
        return {
            resultMsg: ''
        }
    },

    computed: {
        orderResult() {
            return this.$store.state.orderResult;
        },

        orderId() {
            return this.$store.state.orderId;
        }
    },

    created() {
        this.createMsg();
    },

    beforeDestroy() {
        this.$store.commit(Constant.RESET_ORDER_ID);
        this.$store.commit(Constant.RESET_ORDER_RESULT);
    },

    methods: {
        createMsg() {
            if(this.orderResult) {
                this.resultMsg = '주문이 완료되었습니다.';
            } else {
                this.resultMsg = '주문이 실패하였습니다.';
            }
        }
    }

}
</script>

<style scoped>

</style>