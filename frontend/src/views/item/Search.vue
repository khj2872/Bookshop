<template>
<!--    <div>-->
<!--        <h2>검색결과</h2>-->
<!--        <table>-->
<!--            <thead>-->
<!--            <tr>-->
<!--                <th>이미지</th>-->
<!--                <th>이름</th>-->
<!--                <th>가격</th>-->
<!--                <th>저자</th>-->
<!--            </tr>-->
<!--            </thead>-->
<!--            <tbody>-->
<!--            <tr v-for="item in itemResult" :key="item.itemid">-->
<!--                <td><img v-bind:src="item.image" alt="Image placeholder" class="img-fluid"></td>-->
<!--                <td>{{item.name}}</td>-->
<!--                <td>{{item.price}}</td>-->
<!--                <td>{{item.writer}}</td>-->
<!--            </tr>-->
<!--            </tbody>-->
<!--        </table>-->
<!--    </div>-->
    <div class="site-section">
        <div class="container">
            <div class="row mb-5">
                <div class="col-md-9 order-2">
                    <div class="row">
                        <div class="col-md-12 mb-5">
                            <div class="float-md-left mb-4"><h2 class="text-black h5"></h2></div>
                            <div class="d-flex">
                                <div class="dropdown mr-1 ml-md-auto">
                                    <select v-model="searchOption" class="form-control">
                                        <option value="accuracy">정확도순</option>
                                        <option value="lowest">최저가</option>
                                        <option value="highest">최고가</option>
                                        <option value="avgRating">평점순</option>
                                        <option value="review">리뷰순</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row mb-5">
                        <div class="col-sm-6 col-lg-4 mb-4" data-aos="fade-up" v-for="item in itemList.items" :key="item.item_id">
                            <div class="block-4 text-center border">
                                <figure class="block-4-image">
                                    <router-link v-bind:to="'/itemDetail/'+item.item_id"><img v-bind:src="item.image" alt="Image placeholder" class="img-fluid"></router-link>
                                </figure>
                                <div class="block-4-text p-4">
                                    <router-link v-bind:to="'/itemDetail/'+item.item_id">{{item.name}}</router-link>
                                    <p class="text-primary font-weight-bold">{{item.price}}원</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <vue-ads-pagination
                            :total-items="itemList.totalItems"
                            :items-per-page="itemList.size"
                            :page="itemList.page"
                            :loading="loading"
                            @page-change="search"
                    >
                        <template slot-scope="props">
                            <div class="vue-ads-pr-2 vue-ads-leading-loose">
                                Items {{ props.start }} to {{ props.end }} total {{ props.total }}
                            </div>
                        </template>
                        <template
                                slot="buttons"
                                slot-scope="props"
                        >
                            <vue-ads-page-button
                                    v-for="(button, key) in props.buttons"
                                    :key="key"
                                    v-bind="button"
                                    @page-change="itemList.page = button.page"
                            />
                        </template>
                    </vue-ads-pagination>
                </div>

            </div>
        </div>
    </div>
</template>

<script>
import '../../../node_modules/@fortawesome/fontawesome-free/css/all.css';
import '../../../node_modules/vue-ads-pagination/dist/vue-ads-pagination.css';
import VueAdsPagination, { VueAdsPageButton } from 'vue-ads-pagination';
import Constant from '../../store/Constant';

export default {
    data() {
        return {
            itemList: {
                page:0, size:Constant.PAGE_SIZE, totalPage:0, totalItems: 0,items:[]
            },
            searchOption: 'accuracy',
            loading: false
        }
    },

    components: {
        VueAdsPagination,
        VueAdsPageButton
    },

    created() {
        this.search();
    },

    watch: {
        userQuery() {
            this.optionInit();
            this.search();
        },

        searchOption() {
            this.itemList.page = 0;
            this.search();
        },

        'itemList.page' : function () {
            this.search(this.itemList.page);
        }
    },

    computed: {
        userQuery() {
            return this.$store.state.userQuery;
        }
    },

    methods: {
        optionInit() {
            this.searchOption = 'accuracy';
            this.itemList.page = 0;
        },

        search() {
            const param = {
                userQuery : this.userQuery,
                searchOption : this.searchOption,
                page : this.itemList.page,
                size : this.itemList.size
            };
            this.loading = true;
            this.$store.dispatch(Constant.START_SEARCH, param)
                .then(response => {
                    console.log(response);
                    this.itemList.items = response.data.items;
                    this.itemList.totalItems = response.data.totalItems;
                    this.loading = false;
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