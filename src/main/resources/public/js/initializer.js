require.config({
    paths: {
        jquery: 'vendor/jquery-1.10.2.min',
        underscore: 'vendor/lodash',
        backbone: 'vendor/backbone.min',
        text: 'vendor/plugins/text',
        bootstrap: '//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js'
    }

});

require([
    'app'
], function (App) {
    App.initialize();
});