<template>
  <div id="app">
    <Header :user="user"/>
    <Middle :posts="posts" :users="users" :user="user"/>
    <Footer/>
  </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";
import axios from "axios"

export default {
  name: 'App',
  components: {
    Footer,
    Middle,
    Header
  },
  data: function () {
    return {
      user: null,
      posts: [],
      users: []
    }
  },

  beforeMount() {
    if (localStorage.getItem("jwt") && !this.user) {
      this.$root.$emit("onJwt", localStorage.getItem("jwt"));
    }

    axios.get("/api/1/posts").then(response => {
      this.posts = response.data;
    });

    axios.get("/api/1/users").then(response => {
      this.users = response.data;
    });
  },

  updated() {
    setTimeout(() => {
      axios.get("/api/1/posts").then(response => {
        this.posts = response.data;
      });

      axios.get("/api/1/users").then(response => {
        this.users = response.data;
      });
    }, 1000);
  },

  beforeCreate() {
    this.$root.$on("onEnter", (login, password) => {
      if (password === "") {
        this.$root.$emit("onEnterValidationError", "Password is required");
        return;
      }

      axios.post("/api/1/jwt", {
        login, password
      }).then(response => {
        localStorage.setItem("jwt", response.data);
        this.$root.$emit("onJwt", response.data);
      }).catch(error => {
        this.$root.$emit("onEnterValidationError", error.response.data);
      });
    });

    this.$root.$on("onRegister", (login, password) => {
      if (login === "") {
        this.$root.$emit("onRegisterValidationError", "Login is required");
        return;
      }

      if (password === "") {
        this.$root.$emit("onRegisterValidationError", "Password is required");
        return;
      }

      axios.post("/api/1/users", {
        login, password
      }).then(() => {
        this.$root.$emit("onEnter", login, password);
      }).catch(error => {
        this.$root.$emit("onRegisterValidationError", error.response.data);
      })
    });

    this.$root.$on("onWritePost", (title, text) => {
      const authorJwt = localStorage.getItem("jwt");
      if (authorJwt === null) {
        this.$root.$emit("onWritePostValidationError", "You must be logged in to write posts");
      } else {
        axios.post("/api/1/posts", {
          title, text, authorJwt
        }).then(() => {
          axios.get("/api/1/posts").then(response => {
            this.posts = response.data;
          });
          this.$root.$emit("onChangePage", "Index");
        }).catch(error => {
          this.$root.$emit("onWritePostValidationError", error.response.data);
        })
      }
    })

    this.$root.$on("onJwt", (jwt) => {
      localStorage.setItem("jwt", jwt);

      axios.get("/api/1/users/auth", {
        params: {
          jwt
        }
      }).then(response => {
        this.user = response.data;
        this.$root.$emit("onChangePage", "Index");
      }).catch(() => this.$root.$emit("onLogout"))
    });

    this.$root.$on("onLogout", () => {
      localStorage.removeItem("jwt");
      this.user = null;
    });

  }
}
</script>

<style>
#app {

}
</style>
