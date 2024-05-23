<template>
  <div>
    Lorem ipsum dolor sit amet, consectetur adipisicing elit.
    <div class="posts" v-for="post in posts" :key="post.id">
      <Post :post="post" :postId="postId" :comments="comments" :getUserById="getUserById" :commentaryCounter="commentaryCounter"/>
    </div>
  </div>
</template>

<script>


import Post from "@/components/page/Post.vue";

export default {
  name: "Index",
  components: {Post},
  props: ["posts", "postId", "comments", "getUserById", "commentaryCounter"],
}
</script>

<style scoped>

</style>
