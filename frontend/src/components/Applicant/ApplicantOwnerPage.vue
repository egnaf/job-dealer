<template>
  <div>
    <div class="left-sidebar" style="text-align: center; margin: 10px 0 0 0;">
    </div>
    <div class="content">
      <v-data-table
          :headers="headers"
          :items="responses"
          class="elevation-1"
          sort-by="calories"
      >
        <template v-slot:top>
          <v-toolbar color="white" flat>
            <v-toolbar-title>Responces</v-toolbar-title>
          </v-toolbar>
        </template>

        <template v-slot:item.action="{ item }">

          <v-icon
              @click="route(item)"
          >
            mdi-file-document-box
          </v-icon>
        </template>
        <template v-slot:no-data>
          You have no active responses
        </template>
      </v-data-table>
    </div>
    <div class="right-sidebar">
    </div>
  </div>
</template>

<script>

  import {urlPort} from "../../tool";

  export default {
    name: "applicantOwnerPage",
    data: function () {
      return {
        responses: [],
        headers: [
          {text: 'Vacancy name', value: 'vacancyName',},
          {text: 'Status', value: 'status',},
          {text: 'Company', value: 'vacancyCompanyName',},
          {text: 'Actions', value: 'action', sortable: false},
        ],
      }
    },
    created() {
      urlPort.get("/responses/my")
        .then(resp => {
          this.responses = resp.data
        });
    },
    methods: {

      route(item) {
        this.$router.push('/vacancies/' + item.vacancyId);
      },
    },
  }

</script>

<style scoped>

</style>