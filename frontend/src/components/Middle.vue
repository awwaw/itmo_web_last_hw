<template>
  <div class="middle">
    <Sidebar :posts="viewPosts"/>
    <main>
      <Index v-if="page === 'Index'"
             :posts="posts"
             :getUserById="getUserById"/>
      <Users v-if="page === 'Users'" :users="users"/>
      <WritePost v-if="page === 'WritePost'" :user="user"/>
      <Enter v-if="page === 'Enter'"/>
      <Register v-if="page === 'Register'"/>
    </main>
  </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./main/Index";
import Enter from "./main/Enter";
import Register from "./main/Register";
import Users from "@/components/page/Users.vue";
import WritePost from "@/components/page/WritePost.vue";

export default {
  name: "Middle",
  data: function () {
    return {
      page: "Index"
    }
  },
  components: {
    WritePost,
    Users,
    Register,
    Enter,
    Index,
    Sidebar
  },
  props: ["posts", "users", "user"],
  computed: {
    viewPosts: function () {
      return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
    }
  },

  methods: {
    getUserById: function (id) {
      return Object.values(this.users).filter(usr => usr.id === id)[0]["login"];
    },

    getCommentsByPost: function (id) {
      return Object.values(this.comments).filter(comment => comment.post.id === id);
    },

    commentaryCounter: function (id) {
      return Object.values(this.comments).filter(comment => comment.post.id === id).length;
    },
  },

  beforeCreate() {
    this.$root.$on("onChangePage", (page) => this.page = page)
  }
}
</script>

<style scoped>

</style>
