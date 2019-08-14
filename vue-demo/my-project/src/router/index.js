import Vue from 'vue'
import Router from 'vue-router'
import Home from "../views/home"
import User from "../views/user"
import Layout from '../components/Layout'

Vue.use(Router)

const routes = [
    {
        path: '/',
        component: Layout,
        redirect: '/home',
        children: [{
            path: '/home',
            name: 'home',
            component: Home
        }]
    },
    {
        path: "/user",
        component: User
    }
]

var router = new Router({
    routes
})
export default router