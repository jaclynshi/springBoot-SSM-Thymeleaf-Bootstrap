//定义控制器View1Controller，并注入$rootScope、$scope、$http
actionApp.controller('View1Controller', ['$rootScope', '$scope', '$http', function($rootScope, $scope,$http) {
    //使用$scope.$on监听$viewContentLoaded事件，可以在页面内容加载完成后进行一些操作
    $scope.$on('$viewContentLoaded', function() {
        console.log('页面加载完成');
    });
    
    
    $scope.search = function(){ //在scope内定义一个方法search，在页面上通过ng-click调用。
      personName = $scope.personName;   //获取页面上定义的ng-model="personName"的值
      $http.get('search',{  //向服务端地址search发送get请求。
          params:{personName:personName}   //增加请求参数
      }).success(function(data){  //请求成功回调
         $scope.person=data;//将服务端返回的数据data通过$scope.person赋值给模型person，这样页面视图上可以通过{{person.name}}来调用，且模型person值改变后，视图是自动更新的。
      });;
     
    };
}]);
 
actionApp.controller('View2Controller', ['$rootScope', '$scope',  function($rootScope, $scope) {
    $scope.$on('$viewContentLoaded', function() {
        console.log('页面加载完成');
    });
}]);