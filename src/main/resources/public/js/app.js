define([
    'jquery',
    'underscore',
    'backbone',
    'router',
    'initializer'
], function ($, _, Backbone, AppRouter, Bootstrap) {
    var App = {
        initialize: function () {
            var router = new AppRouter();
            Backbone.history.start();
        }
    };

    return App;
});