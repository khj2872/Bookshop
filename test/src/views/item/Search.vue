<template>
    <div>
        <h2>검색결과</h2>
        <table>
            <thead>
            <tr>
                <th>이미지</th>
                <th>이름</th>
                <th>가격</th>
                <th>저자</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="item in itemResult" :key="item.itemid">
                <td><img v-bind:src="item.image" alt="Image placeholder" class="img-fluid"></td>
                <td>{{item.name}}</td>
                <td>{{item.price}}</td>
                <td>{{item.writer}}</td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import Constant from '../../store/Constant';
export default {
    data: () => {
        return {
            from : 0,
            size : 20,
            itemResult : []
        }
    },

    created() {
        this.search(this.userQuery);
    },

    watch: {
        userQuery() {
            this.search(this.userQuery);
        }
    },

    computed: {
        userQuery() {
            return this.$store.state.userQuery;
        }
    },

    methods: {
        search(userQuery) {
            this.$store.dispatch(Constant.START_SEARCH, { userQuery : userQuery })
                .then(response => {
                    console.log(response);
                    this.itemResult = response.data;
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