# RxJavaFilterOperatorDemo
## RxJava中部分转换类操作符，包括Filter()、Take()、TakeLast()、TakeUntil()、Skip()、SkipLast()、ElementAt()、Debounce()、 DistinctUntilChange()、First()、Last()
### (1)Filter
filter(Func1)用来过滤被观察者中我们不想要的数据，只返回满足条件的值。<br>
根据官方给出的原理图：<br>
			 
       
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
