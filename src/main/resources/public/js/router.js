define([
    'jquery',
    'underscore',
    'backbone',
    'views/main-page',
    'views/dashboard',
    'views/event',
    'views/twitter'
], function ($, _, Backbone, MainPageView, DashboardView, TwitterView, EventView) {
    var AppRouter = Backbone.Router.extend({
        routes: {
            '!/connect/twitter': 'dashboard',
            '!/dashboard': 'dashboard',
            '!/login': 'twitter',
            '!/event/:id': 'event',
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

        event: function (id) {
            var eventView = new EventView({
                el: $('.sub-cont'),
                id: id
            });
            eventView.render();
        },

        twitter: function () {
            var twitterView = new TwitterView({
                el: $('.sub-cont')
            });
            twitterView.render();
        }

    });

    return AppRouter;
});