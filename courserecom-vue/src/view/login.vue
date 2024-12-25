<template>
  <div class="login-container">
    <!-- Card -->
    <div class="card" style="height: 400px;width: 500px;">
      <select v-model="role" style="width: 80px;height: 40px;font-size:16px">
        <option value="student">学生</option>
        <option value="admin">管理员</option>
      </select>

      <form v-if="role === 'student'">
        <input type="text" v-model="student.UAccount" placeholder="Username">
        <input type="password" v-model="student.UPassword" placeholder="Password">
        <button @click.prevent="login('student')" style="background-color: #83b3f3">学生登录</button>
        <p v-if="role === 'student'" style="margin-top: 20px;color: #174e90">
          <a href="#" @click="showRegistration = true">注册账号</a>
        </p>
      </form>

      <form v-if="role === 'admin'">
        <input type="text" v-model="admin.UAccount" placeholder="Username">
        <input type="password" v-model="admin.UPassword" placeholder="Password">
        <button @click.prevent="login('admin')" style="background-color: #83b3f3">管理员登录</button>
      </form>

      <!-- Registration Modal -->
      <div v-if="showRegistration" class="registration-modal">
        <h3>Registration</h3>
        <el-form ref="form" :model="newUser" label-width="100px">
          <el-form-item label="账号" prop="UAccount" required>
            <el-input v-model="newUser.UAccount" placeholder="UAccount"></el-input>
          </el-form-item>
          <el-form-item label="昵称" prop="Uname" required>
            <el-input v-model="newUser.Uname" placeholder="Uname"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="UPassword" required>
            <el-input type="password" v-model="newUser.UPassword" placeholder="UPassword"></el-input>
          </el-form-item>
          <el-button @click="registerUser" style="margin: 5px">提交</el-button>
          <el-button @click="showRegistration = false" style="margin: 5px">返回</el-button>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import router from "@/router/index.js";
import axios from "axios";
import {ElMessage} from "element-plus";

export default {
  components: {},
  data() {
    return {
      role: 'student',
      student: {
        UAccount: '',
        UPassword: ''
      },
      admin: {
        UAccount: '',
        UPassword: ''
      },
      showRegistration: false,
      newUser: {
        UAccount: '',
        Uname: '',
        UPassword: ''
      }
    };
  },
  methods: {
    async login(role) {
      try {
        const formData = {
          uaccount: role === 'student' ? this.student.UAccount : this.admin.UAccount,
          upassword: role === 'student' ? this.student.UPassword : this.admin.UPassword
        };
        console.log("登录：",formData)
        const response = await axios.post('http://localhost:8090/user/login', formData);
        console.log("res:",response.data[0]);
        if (response.data !== null) {
          this.rUser = response.data[0];
          console.log("role:",this.rUser.utype)
          if (role === 'admin') {
            if (this.rUser.utype === 0) {
              // Navigate to admin home and pass UAccount
              this.$router.push({ name: 'AdminHome' });
            } else {
              alert('登录失败，权限不足');
            }
          } else {
            this.$router.push({ name: 'StudentHome'});
          }
        } else {
          alert('登录失败，请检查用户名和密码是否正确');
        }
      } catch (error) {
        console.error('登录请求失败:', error);
        if (error.response) {
          alert('登录请求失败，错误信息: ' + error.response.data.message);
        } else {
          alert('登录请求失败，错误信息: ' + error.message);
        }
      }
    },
    async registerUser() {
      const formData = {
        uaccount: this.newUser.UAccount,
        uname: this.newUser.Uname,
        upassword: this.newUser.UPassword,
        utype: 1
      };
      if (formData.uaccount === '') {
        alert('请输入账户！');
      } else if (formData.uname === '') {
        alert('请输入昵称！');
      } else if (formData.upassword === '') {
        alert('请输入密码！');
      } else {
        try{
          const response = await axios.post('http://localhost:8090/user/register', formData);
          console.log("返回",response.data);
          if (response.data.status === 200) {
            this.$router.push('/');
            alert('注册成功！请登录。');
          } else if (response.data.status === 401) {
            alert('账号已存在！');
          }else{
            alert('注册失败！');
          }
        }catch (error) {
          console.error('注册请求失败:', error);
          // 检查是否有响应数据
          if (error.response) {
            alert('注册请求失败，错误信息: ' + error.response.data.message);
          } else {
            alert('注册请求失败，错误信息: ' + error.message);
          }
        }
      }
    }
  }
};
</script>

<style scoped>
.login-container {
  background-image: url('https://tse1-mm.cn.bing.net/th/id/OIP-C.KknZ82d9g6mi2ISfFEK7IgHaEK?w=309&h=180&c=7&r=0&o=5&dpr=1.5&pid=1.7&#39;');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  font-family: Arial, sans-serif;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh; /* 确保高度铺满整个视口 */
  width: 100vw; /* 确保宽度铺满整个视口 */
  margin: 0;
  background-color: #f4f4f4;
}

.card {
  background: white;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  width: 100vw; /* 调整卡片宽度铺满整个视口 */
  height: 100vh; /* 调整卡片高度铺满整个视口 */
}

.logo {
  position: absolute;
  top: 0;
  left: 0;
}

input[type="text"], input[type="password"], input[type="email"], input[type="address"] {
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  border: 1px solid #ddd;
  border-radius: 5px;
}

button {
  width: 100%;
  padding: 10px;
  border: none;
  border-radius: 5px;
  background-color: #60bde1;
  color: white;
  cursor: pointer;
}

button:hover {
  background-color: #2dacda;
}

.registration-modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: white;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  width: 500px;
  height: 400px;
}
</style>
