define([
    'jquery',
    'underscore',
    'backbone',
    'views/main-page',
    'views/dashboard',
    'views/twitter'
], function ($, _, Backbone, MainPageView, DashboardView, TwitterView) {
    var AppRouter = Backbone.Router.extend({
        routes: {
            '!/connect/twitter': 'dashboard',
            '!/login': 'twitter',
            '!': 'mainPage'
        },

        mainPage: function () {
            var mainPageView = new MainPageView({
                el: $('.sub-cont')
            });
            mainPageView.render();
        },

        dashboard: function () {
            var dashboardView = new DashboardView({
                el: $('.sub-cont')
            });
            dashboardView.render();
        },

        twitter: function () {
            var twitterView = new TwitterView({
                el: $('.sub-cont')
            });
            twitterView.render();
        },

    });

    return AppRouter;
});