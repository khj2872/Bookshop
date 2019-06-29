<template>
    <header class="site-navbar" role="banner">
      <div class="site-navbar-top">
        <div class="container">
          <div class="row align-items-center">

            <!--<div class="col-6 col-md-4 order-2 order-md-1 site-search-icon text-left">-->
              <!--<form action="" class="site-block-top-search">-->
                <!--<span class="icon icon-search2"></span>-->
                <!--<input type="text" placeholder="Search">-->
              <!--</form>-->
            <!--</div>-->
            
            <div class="col-12 mb-3 mb-md-0 col-md-4 order-1 order-md-2 text-left">
              <div class="site-logo">
                <router-link to="/" class="js-logo-clone">KOBOBOOK</router-link>
              </div>
            </div>

            <div class="col-12 mb-3 mb-md-0 col-md-4 order-1 order-md-2 text-center" v-on-clickaway="focusOut">
              <input type="text" class="form-control" placeholder="Search" @focusin="typeKeyword"
                     v-on:input="typeKeyword" v-on:keydown="moveAutocompleteItem" v-bind:value="userQuery" v-on:keyup.enter="goSearch">
              <ul class="suggestion">
                <li v-for="item in autoCompleteList" v-bind:class="{active:item.active}"
                    @mouseover="mouseOverAutocomplete(item)" @click="selectAutocompleteKeyword(item)">{{item.name}}</li>
              </ul>
            </div>

            <div class="col-6 col-md-4 order-3 order-md-3 text-right">
              <router-link v-if="!loggedIn" to="/login">LOGIN</router-link>
              <router-link v-if="loggedIn" to="/myPage">{{username}}</router-link>
              <a href="" v-if="loggedIn" @click.prevent="onClickLogout()">LOGOUT</a>
            </div>

          </div>
        </div>
      </div> 
      <nav class="site-navigation text-right text-md-center" role="navigation">
        <div class="container">
          <ul class="site-menu js-clone-nav d-none d-md-block">
            <li class="has-children active">
              <!--<a href="">Home</a>-->
              <router-link to="/">Home</router-link>
              <ul class="dropdown">
                <li><router-link to="/item">ITEM</router-link></li>
                <li><a href="#">Menu One</a></li>
                <li><a href="#">Menu Two</a></li>
                <li><a href="#">Menu Three</a></li>
                <li class="has-children">
                  <a href="#">Sub Menu</a>
                  <ul class="dropdown">
                    <li><a href="#">Menu One</a></li>
                    <li><a href="#">Menu Two</a></li>
                    <li><a href="#">Menu Three</a></li>
                  </ul>
                </li>
              </ul>
            </li>
            <li class="has-children">
              <router-link to="#">카테고리</router-link>
              <ul class="dropdown">
                  <li v-for="category in categories"><router-link v-bind:to="'/category/'+category.id">{{category.name}}</router-link></li>
              </ul>
            </li>
            <li><a href="#">Shop</a></li>
            <li><a href="#">Catalogue</a></li>
            <li><a href="#">Contact</a></li>
          </ul>
        </div>
      </nav>
    </header>
</template>

<script>
import { mapState } from 'vuex'
import Constant from '../../store/Constant';
import { directive as onClickaway} from 'vue-clickaway';
import each from 'foreach';

export default {
  data: () => {
    return {
      categories : '',
      userQuery : '',
      autoCompleteList : []
    }
  },

  directives : {
    onClickaway : onClickaway
  },

  computed: mapState(['username',['loggedIn']]),

  created() {
    this.getCategories();
  },

  methods: {
    onClickLogout() {
      // LOGOUT 변이 실행 후 리다이렉트
      this.$store.dispatch(Constant.LOGOUT)
              .then(()=> {
                alert('로그아웃 되었습니다.');
                this.$router.push('/');
              })
    },

    getCategories() {
      this.$store.dispatch(Constant.GET_CATEGORIES)
              .then((response)=> {
                console.log(response);
                this.categories = response.data;
              })
              .catch(ex=> {
                console.log(ex);
              })
    },

    /**
     * 키워드 검색 시 자동완성어 검색
     * @param e
     */
    typeKeyword(e) {
      this.userQuery = e.target.value;
      console.log(e.target.value);
      if(this.userQuery === "") {
        this.autoCompleteList = [];
      } else {
        this.getAutoCompleteResult();
      }
    },

    /**
     * 자동완성 결과 검색을 한다.
     */
    getAutoCompleteResult() {
      console.log(this.userQuery);
      this.$store.dispatch(Constant.GET_AUTOCOMPLETE, { userQuery : this.userQuery })
              .then((response) => {
                this.autoCompleteList = response.data;
              })
              .catch(ex => {
                console.log(ex);
              })
    },

    /**
     * 자동완성에서 위, 아래 버튼을 누를 경우, 자동완성 포커스를 이동한다.
     */
    moveAutocompleteItem(event) {
      //top
      if(event.keyCode === 38) {
        this.goUpKeyword();
      }
      //bottom
      else if(event.keyCode === 40) {
        this.goDownKeyword();
      }
    },

    /**
     * 자동완성 펼친 상태에서 아래 버튼 누를시 포커스 이동을 한다.
     * @returns {boolean}
     */
    goDownKeyword() {
      if(this.autoCompleteList !== [] && this.autoCompleteList !== null && this.autoCompleteList !== ''){
        if(typeof this.autoCompleteList.selected === "undefined"){
          each(this.autoCompleteList, (value, key, array) => {
            if(key === 0){
              value.active = true;
              this.userQuery = value.name;
              this.autoCompleteList.selected = key;
            }
          })
        }else if((this.autoCompleteList.selected+2) > this.autoCompleteList.length){
          return false;
        }else{
          this.autoCompleteList.selected = this.autoCompleteList.selected + 1;
          each(this.autoCompleteList, (value, key, array) => {
            if(key === this.autoCompleteList.selected){
              value.active = true;
              this.userQuery = value.name;
              this.autoCompleteList.selected = key;
            }else{
              value.active = false;
            }
          })
        }
        this.$forceUpdate()
      }
    },

    /**
     * 자동완성 펼친 상태에서 위 버튼 누를시 포커스 이동을 한다.
     * @returns {boolean}
     */
    goUpKeyword() {
      if(this.autoCompleteList !== [] && this.autoCompleteList !== null && this.autoCompleteList !== ''){
        if(typeof this.autoCompleteList.selected == "undefined"){
          each(this.autoCompleteList, (value, key, array) => {
            if(key === 0){
              value.active = true;
              this.userQuery = value.name;
              this.autoCompleteList.selected = key;
            }
          })
        } else if(this.autoCompleteList.selected === 0) {
          return false;
        } else {
          this.autoCompleteList.selected = this.autoCompleteList.selected - 1;
          each(this.autoCompleteList, (value, key, array) => {
            if(key === this.autoCompleteList.selected){
              value.active = true;
              this.userQuery = value.name;
              this.autoCompleteList.selected = key;
            }else{
              value.active = false;
            }
          })
        }
        this.$forceUpdate()
      }
    },

    /**
     *  자동완성어 리스트에 마우스 올릴 때 커서를 해당 자동완성어로 이동한다.
     * @param item
     */
    mouseOverAutocomplete : function (item) {
      each(this.autoCompleteList, (value, key, array) => {
        if(item.name === value.name){
          value.active = true;
          this.autoCompleteList.selected = key;
        }else{
          value.active = false;
        }
      });
      this.$forceUpdate();
    },

    /**
     * 자동완성 포커스 아웃 시 자동완성을 초기화를 한다.
     */
    focusOut() {
      this.autoCompleteList = [];
    },

    /**
     * 자동완성어 클릭시 검색을 한다.
     * @param item
     */
    selectAutocompleteKeyword(item) {
      this.userQuery = item.name;
      this.autoCompleteList = [];
      this.goSearch();
    },

    /**
     * 사용자의 질의 검색
     */
    goSearch : function () {
      this.focusOut();
      this.startSearch();
    },

    /**
     * 검색을 시작 한다
     */
    startSearch : function () {
      this.$store.commit(Constant.USER_QUERY, this.userQuery);
      this.$router.push('/search')
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .suggestion {
    list-style-type: none;
    padding: 0;
    border : 1px solid lightgray;
    text-align: left;
    font-size:smaller;
  }

  .suggestion li {
    font-size: 14px;
    margin:10px 1px;
    border-bottom: 1px solid lightgray;
  }

  .suggestion li:last-child {
    border: none;
  }

  .suggestion .active{
    background-color: #F3F0DC;
  }

  .comma:not(:empty) ~ .comma:not(:empty):before {
    content: ", ";
  }

</style>