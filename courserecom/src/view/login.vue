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
          <el-form-item label="Username" prop="UAccount" required>
            <el-input v-model="newUser.UAccount" placeholder="Username"></el-input>
          </el-form-item>
          <el-form-item label="Password" prop="UPassword" required>
            <el-input type="password" v-model="newUser.UPassword" placeholder="Password"></el-input>
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
      rUser: {
        username: '',
        password: '',
        ucategory: ''
      },
      newUser: {
        UAccount: '',
        UPassword: '',
        UPhone: '',
        UAddress: ''
      }
    };
  },
  methods: {
    async login(role) {
      if (role === 'student') {
        if (this.student.UAccount === 'A1' && this.student.UPassword === 'A1') {
          await router.push("/studenthome");
          alert('学生登录成功');
        } else if (this.student.UAccount === '') {
          alert('请输入账号');
        } else if (this.student.UPassword === '') {
          alert('请输入密码');
        } else {
          alert('账号或密码错误，请重新输入');
        }
      } else {
        if (this.admin.UAccount === 'admin' && this.admin.UPassword === 'admin') {
          await router.push("/adminhome");
          alert('管理员登录成功');
        } else if (this.admin.UAccount === '') {
          alert('请输入账号');
        } else if (this.admin.UPassword === '') {
          alert('请输入密码');
        } else {
          alert('账号或密码错误，请重新输入');
        }
      }
    },
    async registerUser() {
      const formData = {
        uaccount: this.newUser.UAccount,
        upassword: this.newUser.UPassword,
        uphone: this.newUser.UPhone,
        uaddress: this.newUser.UAddress
      };
      if (formData.uaccount === '') {
        alert('请输入账户！');
      } else if (formData.upassword === '') {
        alert('请输入密码！');
      } else {
        this.$router.push('/');
        alert('注册成功！请登录。');
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
