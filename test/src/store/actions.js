import Constant from './Constant'
import axios from 'axios'

export default {
    [Constant.CHANGE_ISLOADING] : (store, payload) => {
        store.commit(Constant.CHANGE_ISLOADING, payload);
    },

    [Constant.OAUTH_LOGIN]: (store, payload) => {
        return new Promise((resolve, reject) => {
            store.dispatch(Constant.CHANGE_ISLOADING, { isloading : true });
            axios.post('/api/members/oauth_login', payload)
                .then(jwt => {
                    // console.log(jwt);
                    store.commit(Constant.ADD_JWT, jwt.data);
                    axios.defaults.headers.common['Authorization'] = jwt.data;
                    store.dispatch(Constant.ADD_USERNAME)
                        .then(()=> {
                            resolve();
                        })
                        .catch(ex=> {
                            reject(ex);
                        })
                })
                .catch(ex => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    reject(ex);
                })
        })

    },

    [Constant.ADD_USERNAME] : (store) => {
        return new Promise((resolve, reject) => {
            store.dispatch(Constant.CHANGE_ISLOADING, { isloading : true });
            axios.get('/api/members/username')
                .then(memberInfo => {
                    // console.log(memberInfo);
                    store.commit(Constant.ADD_USERNAME, memberInfo);
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    resolve();
                })
                .catch(ex => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    reject(ex);
                })
        })

    },

    [Constant.LOGOUT] : (store) => {
        return new Promise((resolve) => {
            axios.defaults.headers.common['Authorization'] = undefined;
            store.commit(Constant.LOGOUT);
            resolve();
        })
    },

    [Constant.REGISTER] : (store, payload) => {
        return new Promise((resolve, reject) => {
            store.dispatch(Constant.CHANGE_ISLOADING, { isloading : true });
            axios.post('/api/members/register', payload)
                .then(response => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    resolve(response.status);
                })
                .catch(ex => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    reject(ex);
                })
        });

    },

    [Constant.LOGIN] : (store, payload) => {
        return new Promise((resolve, reject) => {
            store.dispatch(Constant.CHANGE_ISLOADING, { isloading : true });
            axios.post('/api/members/login', payload)
                .then(jwt => {
                    store.commit(Constant.ADD_JWT, jwt.data);
                    axios.defaults.headers.common['Authorization'] = jwt.data;
                    store.dispatch(Constant.ADD_USERNAME)
                        .then(()=> {
                            resolve();
                        })
                        .catch(ex=> {
                            reject(ex);
                        })
                })
                .catch(ex => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    reject(ex);
                })
        });
    },

    [Constant.GET_CATEGORIES] : () => {
        return new Promise((resolve, reject) => {
            axios.get('/api/categories')
                .then(response => {
                    resolve(response);
                })
                .catch(ex=> {
                    reject(ex);
                })
        })
    },

    [Constant.GET_CATEGORY_ITEMS] : (store, payload) => {
        return new Promise((resolve, reject) => {
            store.dispatch(Constant.CHANGE_ISLOADING, { isloading : true });
            axios.get('/api/items/category/' + payload)
                .then(response => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    resolve(response);
                })
                .catch(ex=> {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    reject(ex);
                })
        })
    },

    [Constant.GET_AUTOCOMPLETE] : (store, payload) => {
        return new Promise((resolve, reject) => {
            axios.get('/api/items/autocomplete', {
                params : payload
            })
                .then(response => {
                    resolve(response);
                })
                .catch(ex => {
                    reject(ex);
                })
        })
    },

    [Constant.START_SEARCH] : (store, payload) => {
        return new Promise((resolve, reject) => {
            store.dispatch(Constant.CHANGE_ISLOADING, { isloading : true });
            axios.defaults.headers.common['Authorization'] = store.state.jwt;
            axios.get('/api/items/search', {
                params : payload
            })
                .then(response => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    resolve(response);
                })
                .catch(ex => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    reject(ex);
                })
        })
    },

    [Constant.GET_ITEM_DETAIL] : (store, payload) => {
        return new Promise((resolve, reject) => {
            store.dispatch(Constant.CHANGE_ISLOADING, { isloading : true });
            axios.defaults.headers.common['Authorization'] = store.state.jwt;
            axios.get('/api/items/' + payload)
                .then(response => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    resolve(response);
                })
                .catch(ex => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    reject(ex);
                })
        })
    },

    [Constant.ADD_CART] : (store, payload) => {
        return new Promise((resolve, reject) => {

            store.dispatch(Constant.CHANGE_ISLOADING, { isloading : true });
            axios.defaults.headers.common['Authorization'] = store.state.jwt;

            console.dir(payload);

            axios.post('/api/carts', payload)
                .then(response => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    resolve(response);
                })
                .catch(ex => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    reject(ex);
                })
        })
    },

    [Constant.DELETE_CART] : (store, payload) => {
        return new Promise((resolve, reject) => {
            store.dispatch(Constant.CHANGE_ISLOADING, { isloading : true });
            axios.defaults.headers.common['Authorization'] = store.state.jwt;
            axios.delete('/api/carts/' + payload)
                .then(response => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    resolve(response);
                })
                .catch(ex => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    reject(ex);
                })
        })
    },

    [Constant.UPDATE_CART] : (store, payload) => {
        return new Promise((resolve, reject) => {

            store.dispatch(Constant.CHANGE_ISLOADING, { isloading : true });
            axios.defaults.headers.common['Authorization'] = store.state.jwt;
            const data = { count : payload.count };

            axios.put('/api/carts/' + payload.id, data)
                .then(response => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    resolve(response);
                })
                .catch(ex => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    reject(ex);
                })
        })
    },

    [Constant.GET_CART_LIST] : (store) => {
        return new Promise((resolve, reject) => {
            store.dispatch(Constant.CHANGE_ISLOADING, { isloading : true });
            axios.defaults.headers.common['Authorization'] = store.state.jwt;
            axios.get('/api/carts')
                .then(response => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    resolve(response);
                })
                .catch(ex => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    reject(ex);
                })
        })
    },

    [Constant.GET_ORDER_LIST] : (store, payload) => {
        return new Promise((resolve, reject) => {
            store.dispatch(Constant.CHANGE_ISLOADING, { isloading : true });
            axios.defaults.headers.common['Authorization'] = store.state.jwt;
            axios.get('/api/orders/list', {
                params : {
                    id : encodeURI(payload)
                }
            })
                .then(response => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    resolve(response);
                })
                .catch(ex => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    reject(ex);
                })
        })
    },

    [Constant.START_ORDER] : (store, payload) => {
        return new Promise((resolve, reject) => {
            store.dispatch(Constant.CHANGE_ISLOADING, { isloading : true });
            axios.defaults.headers.common['Authorization'] = store.state.jwt;
            axios.get('/api/orders/', {
                params : {
                    id : payload
                }
            })
                .then(response => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    resolve(response);
                })
                .catch(ex => {
                    store.dispatch(Constant.CHANGE_ISLOADING, { isloading : false });
                    reject(ex);
                })
        })
    }


}