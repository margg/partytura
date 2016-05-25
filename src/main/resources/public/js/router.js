define([
    'jquery',
    'underscore',
    'backbone',
    'views/dashboard'
], function ($, _, Backbone, DashboardView) {
    var AppRouter = Backbone.Router.extend({
        routes: {
            '!': 'dashboard'
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