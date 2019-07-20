<template>
    <div class="container">
        <div class="card bg-light">
            <article class="card-body mx-auto" >
                <h4 class="card-title mt-3 text-center">회원가입</h4>
                <p class="text-center">Get started with your free account</p>
                    <div class="form-group input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
                        </div>
                        <input v-validate="'required|email'" name="email" data-vv-as="아이디(이메일) " v-model="email" class="form-control" placeholder="아이디(이메일)" type="email">
                        <span class="error" v-if="errors.has('email')">{{errors.first('email')}}</span>
                    </div> <!-- form-group// -->
                    <div class="form-group input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                        </div>
                        <input v-validate="'required|min:6'" name="pw" data-vv-as="비밀번호 " v-model="pw" class="form-control" placeholder="비밀번호" type="password">
                        <span class="error" v-if="errors.has('pw')">{{errors.first('pw')}}</span>
                    </div> <!-- form-group// -->
                    <div class="form-group input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                        </div>
                        <input v-validate="'required'" name="name" data-vv-as="이름 " v-model="name" class="form-control" placeholder="이름" type="text">
                        <span class="error" v-if="errors.has('name')">{{errors.first('name')}}</span>
                    </div> <!-- form-group// -->
                    <div class="form-group input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"> <i class="fa fa-phone"></i> </span>
                        </div>
                        <input v-validate="'required'" name="phone" data-vv-as="휴대폰 번호 " v-model="phone" class="form-control" placeholder="휴대폰 번호" type="text">
                        <span class="error" v-if="errors.has('phone')">{{errors.first('phone')}}</span>
                    </div> <!-- form-group// -->


                    <div class="form-group">
                        <button class="btn btn-primary btn-block" @click="register()"> 회원가입 </button>
                    </div> <!-- form-group// -->
                    <p class="text-center">아이디가 이미 있으신가요? <router-link to="/login">로그인</router-link></p>
            </article>
        </div> <!-- card.// -->
    </div>
    <!--container end.//-->
</template>

<script>
import Constant from '../../store/Constant';

export default {
    data: () => {
        return {
            email : '',
            pw : '',
            name : '',
            phone : ''
        }
    },
    methods: {
        register() {
            this.$validator.validateAll()
                .then(()=>{
                    if (!this.errors.any()) {

                        let payload = {
                            userEmail : this.email,
                            password : this.pw,
                            username : this.name,
                            phone : this.phone
                        };

                        this.$store.dispatch(Constant.REGISTER, payload)
                            .then(() => {
                                alert('회원가입 완료');
                                this.$router.push('/login');
                            })
                            .catch(ex=>{
                                console.log(ex);
                                alert("중복된 이메일입니다.")
                            })
                    }
                    return false;
                });


        }
    }

}
</script>

<style scoped>
    .divider-text {
        position: relative;
        text-align: center;
        margin-top: 15px;
        margin-bottom: 15px;
    }
    .divider-text span {
        padding: 7px;
        font-size: 12px;
        position: relative;
        z-index: 2;
    }
    .divider-text:after {
        content: "";
        position: absolute;
        width: 100%;
        border-bottom: 1px solid #ddd;
        top: 55%;
        left: 0;
        z-index: 1;
    }
</style>