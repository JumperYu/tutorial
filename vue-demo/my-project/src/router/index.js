import Vue from 'vue'
import Router from 'vue-router'
import Login from "../views/login"
import Home from "../views/home"
import User from "../views/user"
import Layout from '../components/Layout'

Vue.use(Router)

const routes = [
    {
        path: '/login',
        component: Login,
        hidden: true
    },
    {
        path: '/',
        component: Layout,
        redirect: '/home',
        children: [{
            path: '/home',
            name: 'home',
            component: Home,
            meta: { title: '用户管理', icon: 'user' },
        }]
    },
    {
        path: "/user",
        component: Layout,
        children: [
            {
                path: '',
                name: 'User',
                component: User,
                meta: { title: '系统管理', icon: 'system' }
            }
        ]
    }
]

var router = new Router({
    routes
})
export default router