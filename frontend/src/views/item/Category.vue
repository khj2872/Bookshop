<template>
    <div class="site-section">
        <div class="container">
            <div class="row mb-5">
                <div class="col-md-9 order-2">
                    <div class="row">
                        <div class="col-md-12 mb-5">
                            <div class="float-md-left mb-4"><h2 class="text-black h5">{{cname}}</h2></div>
                            <div class="d-flex">
                                <div class="dropdown mr-1 ml-md-auto">
                                    <button type="button" class="btn btn-secondary btn-sm dropdown-toggle" id="dropdownMenuOffset" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        Latest
                                    </button>
                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuOffset">
                                        <a class="dropdown-item" href="#">Men</a>
                                        <a class="dropdown-item" href="#">Women</a>
                                        <a class="dropdown-item" href="#">Children</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row mb-5">
                        <div class="col-sm-6 col-lg-4 mb-4" data-aos="fade-up" v-for="item in itemList.items" :key="item.key">
                            <div class="block-4 text-center border">
                                <figure class="block-4-image">
                                    <router-link v-bind:to="'/itemDetail/'+item.id"><img v-bind:src="item.image" alt="Image placeholder" class="img-fluid"></router-link>
                                </figure>
                                <div class="block-4-text p-4">
                                    <router-link v-bind:to="'/itemDetail/'+item.id">{{item.name}}</router-link>
                                    <p class="text-primary font-weight-bold">{{item.price}}원</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-3 order-1 mb-5 mb-md-0">
                    <div class="border p-4 rounded mb-4">
                        <h3 class="mb-3 h6 text-uppercase text-black d-block">Categories</h3>
                        <ul class="list-unstyled mb-0">
                            <li class="mb-1" v-for="category in categories" :key="category.id">
                                <router-link v-bind:to="'/category/'+category.id" class="d-flex">
                                    <span>{{category.name}}</span>
                                </router-link>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Constant from '../../store/Constant';
import each from 'foreach';
export default {
    data: () => {
        return {
            cname: '',
            cid : '',
            categories : [],
            itemList : {
                pageno:1, pagesize:Constant.PAGE_SIZE, totalCount:0, items:[]
            }
        }
    },
    created() {
        this.cid = this.$route.params.cid;
        this.getCategories();
        this.getItemList(this.cid);

    },

    computed: {
        // cname : () => {
        //     each (this.categories, (value, key, array) => {
        //         if(this.cid === value.id) return value.name;
        //     })
        // }
    },

    watch: {
        '$route' : function(to) {
            this.cid = to.params.cid;
            this.getCategoryName(this.cid);
            this.getItemList(this.cid);
        }
    },

    methods: {
        getCategoryName(cid) {
            each (this.categories, (value, key, array) => {
                if(Number(cid) === Number(value.id)) {
                    this.cname = value.name;
                }
            })
        },

        getCategories() {
            this.$store.dispatch(Constant.GET_CATEGORIES)
                .then((response)=> {
                    this.categories = response.data;
                    this.getCategoryName(this.cid);
                })
                .catch(ex=> {
                    console.log(ex);
                })
        },

        getItemList(cid) {
            this.$store.dispatch(Constant.GET_CATEGORY_ITEMS, cid)
                .then((response) => {
                    console.log(response);
                    this.itemList = response.data;
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