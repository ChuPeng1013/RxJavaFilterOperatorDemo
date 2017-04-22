# RxJavaFilterOperatorDemo
## RxJava中部分转换类操作符，包括Filter()、Take()、TakeLast()、TakeUntil()、Skip()、SkipLast()、ElementAt()、Debounce()、 DistinctUntilChange()、First()、Last()
### (1)Filter
filter(Func1)用来过滤被观察者中我们不想要的数据，只返回满足条件的值。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/filter.png)<br>
这个时候我需要得到班级里所有男生的学号和姓名，我们可以这样实现：<br>
```Java
Observable.from(studentList)
        .filter(new Func1<Student, Boolean>()
        {
            public Boolean call(Student student)
            {
                return "男".equals(student.getGender());
            }
        })
        .subscribe(new Action1<Student>()
        {
            public void call(Student student)
            {
                Log.d("filter", "学号：" + student.getNumber()
                        + "  姓名：" + student.getName());
            }
        });
```
从上面的代码中可以看出Func1的两个参数分别表示的是Observable的发射值的类型和是否返回Observable的发射值，当为true时就是要返回发射值，当为false时就是过滤数据，不返回发射值。当点击按钮后在控制台上，可以看到这样的结果：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/filterResult.png)
### (2)Take
take(int)用一个整数n作为一个参数，从原始的序列中发射前n个元素。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/take.png)<br>
现在我们需要获取到班级里的前5个学生的学号和姓名，我们可以这样实现：<br>
```Java
Observable.from(studentList)  
       .take(5)  
       .subscribe(new Action1<Student>()  
       {  
           public void call(Student student)  
           {  
               Log.d("Take", "学号：" + student.getNumber()  
                       + "  姓名：" + student.getName());  
           }  
       }); 
```
从上面的代码中可以看出take()中的参数是多少就代表发射数据源的前多少项数据。当点击按钮后在控制台上，可以看到这样的结果：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/takeResult.png)<br>
### (3)TakeLast
takeLast(int)同样用一个整数n作为参数，只不过它发射的是观测序列中后n个元素。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/takeLast.png)<br>
现在我们需要获取到班级里的后3个学生的学号和姓名，我们可以这样实现：<br>
```Java
Observable.from(studentList)  
        .takeLast(3)  
        .subscribe(new Action1<Student>()  
        {  
            public void call(Student student)  
            {  
                Log.d("takeLast", "学号：" + student.getNumber()  
                        + "  姓名：" + student.getName());  
            }  
        });
```
从上面的代码中可以看出takeLast()中的参数是多少就代表发射数据源的后多少项数据。当点击按钮后在控制台上，可以看到这样的结果：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/takeLastResult.png)<br>
### (4)TakeUntil
takeUntil(Observable)订阅并开始发射原始Observable，同时监视我们提供的第二个Observable，如果第二个Observable发射了一项数据或者发射了一个终止通知，takeUntil()返回的Observable会停止发射原始Observable并终止。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/takeUntil.png)<br>
通过以下一个小例子可以帮助我们更好的理解takeUntil<br>
```Java
Observable<Long> observableA = Observable.interval(300, TimeUnit.MILLISECONDS);  
Observable<Long> observableB = Observable.interval(800, TimeUnit.MILLISECONDS);  
observableA.takeUntil(observableB)  
        .subscribe(new Subscriber<Long>()  
        {  
            public void onCompleted()  
            {  
                Log.d("takeUntil", "完成");  
            }  
  
            public void onError(Throwable e)  
            {  
                Log.d("takeUntil", "错误");  
            }  
  
            public void onNext(Long aLong)  
            {  
                Log.d("takeUntil", "  " + aLong);  
            }  
        });
```
在上面的代码中有两个Observable，分别时observableA和observableB，interval()是RxJava中对时间的操作符，在这里我们就叫做时间操作符，在RxJava中常用的事件操作符还有timer()、interval()、delay()、defer()等等。interval()的主要作用就是创建一个按固定时间间隔发射整数序列的Observable，可用作定时器，再结合上面的代码可以看出，observableA是每隔300毫秒发射一个长整型数，而observableB是每隔800毫秒发射一个长整型数，根据interval()操作符的作用可以看出，当observableA发射了0和1以后，此时时间过去了600毫秒，再过200毫秒就轮到observableB发射一个长整型数，当总时间过了800毫秒以后，observableB将会发射第一个长整型数，此时observableA只发射了0和1，当observableB开始发射时整个流程就会结束，同时将会回调onCompleted()方法。<br>
所以此时控制台将会打印出：0  1  完成<br>
takeUntil(Func1)通过Func1中的call方法来判断是否需要终止发射数据，如果返回true，Observable就继续发射数据，如果返回false，Observable就停止发射数据。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/takeUntil1.png)<br>
现在我们要获得班级中，学号A007之前的所有学生的学号和姓名，我们可以这样实现：<br>
```Java
Observable.from(studentList)  
       .takeUntil(new Func1<Student, Boolean>()  
       {  
           public Boolean call(Student student)  
           {  
               if("A007".equals(student.getNumber()))  
               {  
                   return true;  
               }  
               else  
               {  
                   return false;  
               }  
           }  
       })  
       .subscribe(new Action1<Student>()  
       {  
           public void call(Student student)  
           {  
               Log.d("takeUntil", "学号：" + student.getNumber()  
                       + "  姓名：" + student.getName());  
           }  
       });  
```
从上面的代码中可以看出，打印出了所有学号在A007之前的所有学生，但是要注意的是这里的takeUntil()操作符可不是打印出前7个学生的信息，而是从头顺序的打印出来，直到打印出学号为A007的学生信息后停止。当点击按钮后在控制台上，可以看到这样的结果：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/takeUntilResult.png)<br>
### (5)Skip
skip(int)让我们可以忽略Observable发射的前n项数据。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/skip.png)<br>
### (6)SkipLast
skipLast(int)忽略Observable发射的后n项数据。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/skipLast.png)<br>
### (7)ElementAt
elementAt(int)用来获取元素Observable发射的事件序列中的第n项数据，并当做唯一的数据发射出去。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/elementAt.png)<br>
### (8)Debounce
debounce(long, TimeUnit)过滤掉了由Observable发射的速率过快的数据，如果在一个指定的时间间隔过去了仍旧没有发射一个，那么它将发射最后的那个。通常我们用来结合RxBinding(Jake Wharton大神使用RxJava封装的Android UI组件)使用，防止button重复点击。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/debounce.png)<br>
debounce(Func1)可以根据Func1的call方法中的函数来过滤，Func1中的中的call方法返回了一个临时的Observable，如果原始的Observable在发射一个新的数据时，上一个数据根据Func1的call方法生成的临时Observable还没结束，那么上一个数据就会被过滤掉。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/debounce1.png)<br>
### (9)Distinct
distinct()的过滤规则是只允许还没有发射过的数据通过，所有重复的数据项都只会发射一次，可以用于去除重复数据。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/distinct.png)<br>
```Java
Observable.just(1, 1, 3, 3, 5, 7, 7, 7, 9, 9)  
        .distinct()  
        .subscribe(new Action1<Integer>()  
        {  
            public void call(Integer integer)  
            {  
                Log.d("distinct", String.valueOf(integer));  
            }  
        });
```
程序输出为：1，3，5，7，9<br>
distinct(Func1)参数中的Func1中的call方法会根据Observable发射的值生成一个Key，然后比较这个key来判断两个数据是不是相同，如果判定为重复则会和distinct()一样过滤掉重复的数据项。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/distinct1.png)<br>
现在我们需要得到班级里第一个男同学和第一个女同学的学号和姓名，我们可以这样实现：<br>
```Java
Observable.from(studentList)  
        .distinct(new Func1<Student, String>()  
        {  
            public String call(Student student)  
            {  
                return student.getGender();  
            }  
        })  
        .subscribe(new Action1<Student>()  
        {  
            public void call(Student student)  
            {  
                Log.d("distinct", "学号：" + student.getNumber()  
                        + "  姓名：" + student.getName());  
            }  
        });
```
从上面的代码中可以看出在distinct(Func1)方法中，是通过call()方法返回的值来判断数据是否相同。当点击按钮后在控制台上，可以看到这样的结果：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/distinctResult.png)<br>
### (10)DistinctUntilChanged
distinctUntilChanged()和distinct()类似，只不过它判定的是Observable发射的当前数据项和前一个数据项是否相同。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/distinctUntilChanged.png)<br>
同样还是上面过滤数字的例子：<br>
```Java
Observable.just(1, 1, 3, 3, 5, 7, 7, 7, 9, 9)  
        .distinctUntilChanged()  
        .subscribe(new Action1<Integer>()  
        {  
            public void call(Integer integer)  
            {  
                Log.d("distinctUntilChanged", String.valueOf(integer));  
            }  
        });
```
程序输出为：1，3，5，7，9<br>
distinctUntilChanged(Func1)和distinct(Func1)一样，根据Func1中call方法产生一个Key来判断两个相邻的数据项是否相同。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/distinctUntilChanged1.png)<br>
### (11)First
first()顾名思义，它是的Observable只发送观测序列中的第一个数据项。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/first.png)<br>
现在我们要获取班级中第一个学生的学号和姓名，我们可以这样实现：<br>
```Java
Observable.from(studentList)  
        .first()  
        .subscribe(new Action1<Student>()   
        {  
            public void call(Student student)   
            {  
                Log.d("first", "学号：" + student.getNumber()  
                        + "  姓名：" + student.getName());  
            }  
        });
```
first(Func1)只发送符合条件的第一个数据项。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/first1.png)<br>
现在我们要获取班级中学号为A007的学生的学号和姓名，我们可以这样实现：<br>
```Java
Observable.from(studentList)  
        .first(new Func1<Student, Boolean>()   
        {  
            public Boolean call(Student student)   
            {  
                return "007".equals(student.getNumber());  
            }  
        })  
        .subscribe(new Action1<Student>()   
        {  
            public void call(Student student)   
            {  
                Log.d("first", "学号：" + student.getNumber()  
                        + "  姓名：" + student.getName());  
            }  
        });
```
从上面的代码可以看出，first(Func1)方法中是通过call()方法的返回值来判断是否发射Observable中的数据，当返回值为true时发射数据，当返回值为false时不发射数据。<br>
### (12)Last
last()只发射观测序列中的最后一个数据项，和first()的用法相同，这里就不做过多的介绍。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/last.png)<br>
last(Func1)只发射观测序列中符合条件的最后一个数据项，和first(Func1)的用法相同，这里就不做过多的介绍。<br>
根据官方给出的原理图：<br>
![](https://github.com/ChuPeng1013/RxJavaFilterOperatorDemo/raw/master/last1.png)<br>
### [想了解更多关于Android开发的内容欢迎进入我的CSDN博客](http://blog.csdn.net/zackchu)<br>






