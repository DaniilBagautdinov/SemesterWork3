$(document).ready(function () {
    $("#loginForm").validate({
        rules: {
            username: {
                required: true, minlength: 4, maxlength: 16,
            },

            password: {
                required: true, minlength: 4, maxlength: 16,
            },
        }, messages: {
            username: {
                required: "Обязательно для ввода",
                minlength: "Должно быть больше 4 символов",
                maxlength: "Должно быть меньше 16 символов",
            },

            password: {
                required: "Обязательно для ввода",
                minlength: "Должно быть больше 4 символов",
                maxlength: "Должно быть меньше 16 символов",
            },
        },
    });
});
$(document).ready(function () {
    $("#regForm").validate({
        rules: {
            firstName: {
                required: true, minlength: 2, maxlength: 16,
            },

            lastName: {
                required: true, minlength: 2, maxlength: 16,
            },
            username: {
                required: true, minlength: 4, maxlength: 16,
            },

            password: {
                required: true, minlength: 4, maxlength: 16,
            },
            passwordRepeat :{
                required: true, minlength: 4, maxlength: 16, equalTo: '#password',

            }
        }, messages: {
            firstName: {
                required: "Обязательно для ввода",
                minlength: "Должно быть больше 2 символов",
                maxlength: "Должно быть меньше 16 символов",
            },

            lastName: {
                required: "Обязательно для ввода",
                minlength: "Должно быть больше 2 символов",
                maxlength: "Должно быть меньше 16 символов",
            },

            username: {
                required: "Обязательно для ввода",
                minlength: "Должно быть больше 4 символов",
                maxlength: "Должно быть меньше 16 символов",
            },

            password: {
                required: "Обязательно для ввода",
                minlength: "Должно быть больше 4 символов",
                maxlength: "Должно быть меньше 16 символов",
            },
            passwordRepeat: {
                required: "Обязательно для ввода",
                minlength: "Должно быть больше 4 символов",
                maxlength: "Должно быть меньше 16 символов",
                equalTo : "Пароли не совпадают"
            },
        },
    });
});
