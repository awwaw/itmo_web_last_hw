<template>
  <div>
    <div class="posts" v-for="post in posts" :key="post.id">
      <Post :post="post" :getUserById="getUserById"/>
    </div>
  </div>
</template>

<script>

import Post from "@/components/page/Post.vue";

export default {
  name: "Index",
  components: {Post},
  props: ["posts", "getUserById"],
}
</script>

<style scoped>

</style>
