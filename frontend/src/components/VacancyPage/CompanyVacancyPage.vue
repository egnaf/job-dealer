<template>
  <div>
    <div>
      <div class="left-sidebar" style="text-align: center; margin: 10px 0 0 0;">
      </div>
      <div class="content">
        <v-card>
          <v-card-title>
            <router-link to="/my/vacancies">
              <v-btn class="mr-6" icon>
                <v-icon>mdi-chevron-left</v-icon>
              </v-btn>
            </router-link>

            <span class="subheading">{{ vacancy.name }}</span>
          </v-card-title>
          <v-card-text>
            <div>{{vacancy.description}}</div>
            <div>{{vacancy.money}}</div>
            <div>{{vacancy.ownerName}}</div>
          </v-card-text>

          <div class="skills" style="padding: 10px">
            <div class="skill" v-for="skill in vacancy.requestedSkills">
              <v-chip outlined>
                {{ skill }}
              </v-chip>
            </div>
          </div>
        </v-card>

        <v-card v-if="task.name">
          <v-card-title>
            {{task.name}}
          </v-card-title>
          <v-card-text>
            {{task.description}}
          </v-card-text>
        </v-card>

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
                @click="downloadSubmission(item)"
            >
              mdi-cloud-download-outline
            </v-icon>


            <v-icon @click="accept(item)"
                    v-if="item.status === 'APPLIED'"
            >
              mdi-check-outline
            </v-icon>

            <v-icon
                @click="reject(item)"
                v-if="item.status === 'APPLIED'"
            >
              mdi-close-outline
            </v-icon>
            <v-icon
                @click="route(item)"
            >
              mdi-account-badge-horizontal-outline
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
  </div>
</template>

<script>

  import Router from "../../router"
  import {urlPort} from "../../tool";
  import download from "downloadjs"


  export default {
    name: "companyVacancyPage",
    data: function () {
      return {
        fileBytes: null,
        vacancy: {
          id: Router.currentRoute.params.id,
          name: "",
          description: "",
          money: null,
          ownerName: "",
          requestedSkills: [],
          withTask: null,
        },

        task: {
          id: null,
          name: "",
          description: "",

        },
        responses: [],
        submissions: [],
        headers: [
          {text: 'Applicant name', value: 'applicantName',},
          {text: 'Status', value: 'status',},
          {text: 'Actions', value: 'action', sortable: false},
        ],
      }
    },
    created() {
      urlPort.get("/vacancies/" + this.vacancy.id)
        .then(resp => {
          this.vacancy = resp.data;
          urlPort.get("/vacancies/" + this.vacancy.id + "/responses")
            .then(resp => {
              this.responses = resp.data
            });
          if (this.vacancy.withTask) {
            urlPort.get("/vacancies/" + this.vacancy.id + "/tasks")
              .then(resp => {
                this.task = resp.data;
                urlPort.get("/vacancies/" + this.vacancy.id + "/tasks/" + this.task.id)
                  .then(resp => {
                    this.submissions = resp.data;
                  })
              });

          }
        });
    },
    methods: {
      accept(item) {
        item.status = "INVITED";
        this.setStatus(item);
      },
      reject(item) {
        item.status = "REJECTED";
        this.setStatus(item);
      },
      route(item) {
        //todo: сделать линк на резюме пользователя
      },
      setStatus(item) {
        let params = new URLSearchParams();
        params.append("status", item.status);
        urlPort.post("/responses/" + item.id, params)
      },
      downloadSubmission(item) {
        this.fileBytes = this.submissions.find(function (element, index, array) {
          return element.applicantId === item.applicantId
        }).fileData;
        download(atob(this.fileBytes), item.applicantName + "submission.zip", "application/zip")
      }
    },
  }
</script>

<style scoped>

</style>