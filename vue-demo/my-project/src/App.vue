<template>
    <div id="app">
        <router-link to="/user">User</router-link>
        <router-view />
    </div>
</template>

<script>
    export default {
        name: 'App'
    }
</script>
