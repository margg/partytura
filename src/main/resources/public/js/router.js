define([
    'jquery',
    'underscore',
    'backbone',
    'views/main-page',
    'views/dashboard'
], function ($, _, Backbone, MainPageView, DashboardView) {
    var AppRouter = Backbone.Router.extend({
        routes: {
            '!/dashboard': 'dashboard',
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
        }

    });

    return AppRouter;
});