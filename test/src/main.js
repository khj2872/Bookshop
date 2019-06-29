import Vue from 'vue'
import App from './App.vue'
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import router from './router/router'
import store from './store/store'
import GAuth from 'vue-google-oauth2'
import VeeValidate from 'vee-validate'
import ko from 'vee-validate/dist/locale/ko.js'
import IMP from 'vue-iamport'
import VueDaumPostCode from 'vue-daum-postcode'

Vue.use(GAuth, {
    clientId: '715195752938-dhkksls0crjkm62hu5510dvuu2edvs0n.apps.googleusercontent.com',
    scope: 'profile email https://www.googleapis.com/auth/plus.login'
});

const config = {
    locale: 'ko',
    dictionary: {
        ko
    }
};

Vue.use(VeeValidate, config);

Vue.config.productionTip = false;

Vue.use(IMP, 'imp55695262');
Vue.IMP().load();

Vue.use(VueDaumPostCode);

new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app');