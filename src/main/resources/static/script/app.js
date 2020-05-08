//定义模块actionApp，并依赖于路由模块ngRoute
var actionApp = angular.module('actionApp',['ngRoute']);
//配置路由，并注入$routeProvider来配置
actionApp.config(['$routeProvider' , function($routeProvider) {
    // /oper为路由名称
    $routeProvider.when('/oper', {
        controller: 'View1Controller',          //路由控制器名称
        templateUrl: 'pages/view1.html',     //视图的真正地址
    }).when('/directive', {
        controller: 'View2Controller',
        templateUrl: 'pages/view2.html',
    });
 
}]);