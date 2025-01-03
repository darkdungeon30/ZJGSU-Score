import { createRouter, createWebHistory } from 'vue-router'
import Login from "@/view/login.vue";
import StudentHome from "@/view/Stu-Home.vue";
import AdminHome from "@/view/Admin-Home.vue";
const routes = [
    {
        path: '/',
        name: 'Login',
        component: Login
    },
    {
        path: '/studenthome',
        name: 'StudentHome',
        component: StudentHome
    },
    {
        path: '/adminhome',
        name: 'AdminHome',
        component: AdminHome
    },
];
const router = createRouter({
    history: createWebHistory(),
    routes
})
export default router;

