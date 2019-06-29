<template>
<div class="container">
    <div class="row">
        <div class="main">

            <h3>Log In, or <router-link to="/signUp">Sign Up</router-link>
            </h3>

            <div class="row2">
                <img src="../../assets/google.png" style="width:320px; height:60px" @click="loginGoogle()">
            </div>
            <div class="login-or">
                <hr class="hr-or">
                <span class="span-or">or</span>
            </div>

            <form>
                <div class="form-group">
                    <label for="inputUsernameEmail">Username or email</label>
                    <input type="text" class="form-control" id="inputUsernameEmail" v-model="email">
                </div>
                <div class="form-group">
                    <!--<a class="pull-right" href="#">Forgot password?</a>-->
                    <label for="inputPassword">Password</label>
                    <input type="password" class="form-control" id="inputPassword" v-model="pw">
                </div>
                <div class="checkbox pull-right">
                    <!--<label>-->
                    <!--<input type="checkbox">-->
                        <!--Remember me -->
                    <!--</label>-->
                </div>
            </form>
            <button class="btn btn-primary" @click="login()">로그인</button>

        </div>

    </div>
</div>
</template>

<script>
import Constant from '../../store/Constant';

export default {
    data : () => {
        return {
            // prevRoute : null,
            email : '',
            pw : ''
        }
    },

    // beforeRouteEnter : (to, from, next) => {
    //     next(vm => {
    //         vm.prevRoute = from;
    //     })
    // },
    methods: {
        loginGoogle() {
            this.$gAuth.signIn()
                .then(GoogleUser => {
                    const auth = {
                        oauthId: GoogleUser.getBasicProfile().getId(),
                        userEmail: GoogleUser.getBasicProfile().getEmail(),
                        username: GoogleUser.getBasicProfile().getName()
                    };
                    console.log(GoogleUser);

                    this.$store.dispatch(Constant.OAUTH_LOGIN, auth)
                        .then(()=> {
                            this.$router.push('/');
                        })
                })
                .catch(ex => {
                    console.log(ex);
                })
        },

        login() {
            const loginInfo = {
                userEmail : this.email,
                password : this.pw
            };
            console.log(loginInfo);
            this.$store.dispatch(Constant.LOGIN, loginInfo)
                .then(()=> {
                    this.$router.push('/');
                })
                .catch(ex=> {
                    console.log(ex);
                    alert('로그인 오류');
                })
        }
    }
}
</script>

<style>
body {
    padding-top: 15px;
    font-size: 12px
}

.main {
    max-width: 320px;
    margin: 0 auto;
}

.row2 {
    margin-bottom: 10px
}

.login-or {
    position: relative;
    font-size: 18px;
    color: #aaa;
    margin-top: 10px;
    margin-bottom: 10px;
    padding-top: 10px;
    padding-bottom: 10px;
}

.span-or {
    display: block;
    position: absolute;
    left: 50%;
    top: -2px;
    margin-left: -25px;
    background-color: #fff;
    width: 50px;
    text-align: center;
}

.hr-or {
    background-color: #cdcdcd;
    height: 1px;
    margin-top: 0px !important;
    margin-bottom: 0px !important;
}

h3 {
    text-align: center;
    line-height: 300%;
}
</style>
