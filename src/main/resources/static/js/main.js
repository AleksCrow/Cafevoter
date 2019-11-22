function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

var cafeApi = Vue.resource('/cafes{/id}');  //пишем то, что будет в запросе

Vue.component('cafe-form', {
    props: ['cafes', 'cafeAttr'],
    //чтобы сохранить данные, которые получаем используем блок data:
    data: function () {
        return {
            name: '',
            id: ''
        }
    },
    watch: {
        cafeAttr: function (newVal, oldVal) {
            this.name = newVal.name;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
            '<input type="text" placeholder="Write cafe\'s name" v-model="name"/>' +
            '<input type="button" value="Save" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            var cafe = { name: this.name };

            if (this.id) {
                cafeApi.update({ id: this.id }, cafe).then(result =>      //cafes - то же, что в запросе
                    result.json().then(data => {
                        var index = getIndex(this.cafes, data.id);
                        this.cafes.splice(index, 1, data);
                        this.name = '',
                        this.id = ''
                    })
                )
            } else {
                cafeApi.save({}, cafe).then(result =>
                    result.json().then(data => {
                        this.cafes.push(data);
                        //чтобы после добавления в строке не оставались символы:
                        this.name = ''
                    })
                )
            }
        }
    }
});

Vue.component('cafe-row', {
    props: ['cafe', 'editMethod', 'cafes'],
    template: '<tr><td><i>({{ cafe.id }})</i></td> ' +
        '<td>{{ cafe.name }} </td>' +
        '<td><b> -- {{ cafe.createdDate }} </b></td>' +
        '<td><i><b> >>> {{ cafe.rating }} </b></i></td> ' +
        '<td><input type="button" value="Edit" @click="edit" /></td>' +
        '<td><input type="button" value="X" @click="del" /></td>' +
        '</tr>',
    methods: {
        edit: function () {
            this.editMethod(this.cafe)
        },
        del: function () {
            cafeApi.remove({id: this.cafe.id}).then(result => {
                if (result.ok) {
                    this.cafes.splice(this.cafes.indexOf(this.cafe), 1)
                }
            })
        }
    }
});

Vue.component('cafes-list', {
    props: ['cafes'],
    data: function () {
        return {
            cafe: null
        }
    },
    template:
        '<div style="position: relative; width: 450px;">' +
            '<cafe-form :cafes="cafes" :cafeAttr="cafe"/>' +
            '<cafe-row v-for="cafe in cafes" :key="cafe.id" :cafe="cafe" :key2="cafe.createdDate" :key3="cafe.rating"' +
        ' :editMethod="editMethod" :cafes="cafes"/>' +
        '</div>',
    methods: {
        editMethod: function (cafe) {
            this.cafe = cafe;
        }
    }
});

var app = new Vue({
    el: '#app',
    template:
        '<div>' +
        '<div v-if="!profile">Авторизуйтесь через <a href="/login">Google</a></div>' +
            '<div v-else>' +
                '<div>{{profile.name}}&nbsp;<a href="/logout">Выйти</a></div>' +
                '<cafes-list :cafes="cafes" />' +
            '</div>' +
        '</div>',
    data: {
        cafes: frontendData.cafes,
        profile: frontendData.profile
    },
    created: function () {

    }
});