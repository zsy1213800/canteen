import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Manager',
      component: () => import('@/views/Manager.vue'),
      redirect: '/home',
      children: [
        { path: 'home', name: 'Home', component: () => import('@/views/manager/Home.vue')},
        { path: 'admin', name: 'Admin', component: () => import('@/views/manager/Admin.vue')},
        { path: 'person', name: 'Person', component: () => import('@/views/manager/Person.vue')},
        { path: 'user', name: 'User', component: () => import('@/views/manager/User.vue')},
        { path: 'order', name: 'Order', component: () => import('@/views/manager/Order.vue')},
        { path: 'foods', name: 'Foods', component: () => import('@/views/manager/Foods.vue')},
        { path: 'orderManager', name: 'OrderManager', component: () => import('@/views/manager/OrderManager.vue')},
        { path: 'orderManage', name: 'OrderManage', component: () => import('@/views/manager/OrderManage.vue')},
        { path: 'business', name: 'Business', component: () => import('@/views/manager/Business.vue')},
        { path: 'businessfoods', name: 'BusinessFoods', component: () => import('@/views/manager/BusinessFoods.vue')}
      ]
    },
    { path: '/login', name: 'Login', component: () => import('@/views/Login.vue')},
    { path: '/register', name: 'Register', component: () => import('@/views/Register.vue')},
  ]
})

export default router
