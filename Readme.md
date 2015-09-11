Here are the steps how this stopwatch app was developed in an ATDD/TDD way which is inspired by Steve Freeman's great book [Growing Object-Oriented Software Guided by Tests](http://www.growing-object-oriented-software.com/)

* Write the first scenario "作为一个用户，可以看到计时" with a fake implementation. This scenario means a timer is started automatically after the app starts [Git commit] (https://github.com/nerds-odd-e/stopwatch/commit/adf03fef89486f1dd63355fc53972557f9e5b10f)
    * Create an empty Android app so that Calabash can launch it and run the scenario, no implementation for any steps yet
    * Implement the given step "用户打开应用" with no code since the app is started by Calabash before running the scenario. The scenario keep passing with ignored when and then steps
    * Implement the when step "经过(\d+)秒" with the code sleeping 1 second. The scenario keep passing with ignored then step
    * Implement the then step "看见显示(\d+)秒" with the code checking UI displaying "1s". The scenario now fails since no code implemented yet
    * Fake implementation: Add a text view to the StopwatchActivity (aka. the main activity) with text "1s". This can pass the first scenario and we're sure the validation in the then step works
    * Since almost all the code is pretty straightforward, there is limited refactoring we can do now.
* To pass the first scenario with a real implementation, we need a just enough Stopwatch (domain object). [Git commit] (https://github.com/nerds-odd-e/stopwatch/commit/adf03fef89486f1dd63355fc53972557f9e5b10f)
    * The key part of implementation is a "Stopwatch" (too obvious, right? :) ), so we start with a failing unit test for this domain object. It is all about that after stopwatch just started, the secondPassed method should return 0. (too obvious too ^0^)
    * Passing the first failing test is quite easy, just return 0 in method secondPassed (TDD, as simple as possible, hurray!)
    * No refactoring we can do now (first TDD cycle is done, great!)
    * The second failing test is, after stopwatch started and one second passed, secondPassed method should return 1. Sounds easy, but how we make one second passed in our unit test? Maybe you said just sleep 1 second. Ok, but how we know one second passed in our code? Maybe you said we can get current system time when constructing the Stopwatch object and then get system time again in the secondPassed method. Hold on!! system time is a hard dependency which we should isolate from our code. Fair enough, let's isolate this dependency with the Clock interface and a stubClock in the test. Dependency injection, looks good!
    * Passing the second failing test is not a hard work, right? Once you have the test, it's easy to figure out the implementation.
    * After some refactoring, we get the code as it in Stopwatch class of this commit. And, we also take this chance to refactor the first and second unit tests with a setup for stubClock and Stopwatch.
* Now, we need the last code in the StopwatchActivity to pass the first scenario with a real implementation. [Git commit] (https://github.com/nerds-odd-e/stopwatch/commit/adf03fef89486f1dd63355fc53972557f9e5b10f)
    * We need some nasty code to enable the UI refresh timer to display the second passed (by calling Stopwatch). While the code is nasty, but it works and now the first BDD scenario works without fake implementation!
    * Can we celebrate for the first scenario now? Hold on!! We haven't clean up the nasty code yet. The code about timer, schedule and activity UI update seems to be a different responsibility which should not belong to StopwatchActivity. Let's refactor this and then we get the code of StopwatchActivity and UIRefresher of this commit. Hurray!! Long live clean code.
* What's the second scenario? A good question is raised at this moment "If 1.5 second passed, what will be displayed? 1s or 1.5s or 2s". [Git commit] (https://github.com/nerds-odd-e/stopwatch/commit/adf03fef89486f1dd63355fc53972557f9e5b10f)
    * What we finally agree is that it should display 1s when 1.5 second passed. Since we feel this case is not a key business logic, we decide to just add a unit test for it.
    * So, the third failing unit test is added for this case. It's an easy work since we refactored the test code in previous step already.
    * The code passes the failing test with no change! Don't be surprised, current code of Stopwatch is simple enough to cover this case.
* What's the next step? A stopwatch needs a button to start the timer and it can't just start the timer by launching the app. [Git commit] (https://github.com/nerds-odd-e/stopwatch/commit/5d4eaef1a7f05d538ef48a5cbb8323b52c646722)
    * The given step is changed to "假如用户启动秒表" (meaning given user starts the stopwatch) for a better understanding. The step implementation is changed to tap a button (too obvious, right?)
    * The when step is a bit trick now since we have to wait a short time for the button tap. This is obviously an issue we should address for the Calabash test ... in future
    * No change to the then step
    * Adding a button is such a small step as all code goes to StopwatchActivity without any doubt. And, that's it for this commit.
* Next, we find there is a bug which will cause unnecessary refreshing. So we just fix it and keep all tests passing. [Git commit] (https://github.com/nerds-odd-e/stopwatch/commit/bb5b048db7f9a8cc9a061de5ee3a6c2f70b2b504)
* We then move to a new scenario "作为一个用户，可以暂停计时", meaning the stopwatch can be paused. [Git commit] (https://github.com/nerds-odd-e/stopwatch/commit/e1901c788adb36c698f774a9240066ce1a6e7198)
    * The given and then step were created already with the first scenario.
    * The when step which pausing the stopwatch and then wait one second (to make sure the stopwatch really paused!) is implemented with some similar code from the first scenario.
    * At this point, the second scenario is failed since there is no implementation yet
* To implement the second scenario, let's add the pause feature for the Stopwatch class first (starting from domain object). [Git commit] (https://github.com/nerds-odd-e/stopwatch/commit/76d3cf446a541340fd807e056e0db34ef70a52e7)
    * The new failing unit test is doing the exactly the same thing as the second scenario. With previous test refactoring, it's easy to write.
    * The code passing this unit test looks a bit complex since this Stopwatch class now have a mutable status.
    * We refactor the code a bit to keep it clean
* Now let's pass the second scenario with one line code in StopwatchActivity, Hurray! [Git commit] (https://github.com/nerds-odd-e/stopwatch/commit/fa032e53a88d70528014e5cf912ffe954dd30d1f)
* The third scenario (faster, right?) is "作为一个用户，可以恢复计时", meaning the stopwatch can be resumed. [Git commit] (https://github.com/nerds-odd-e/stopwatch/commit/2194b3cf34a4dc98f2c7c99c0c9bd93b034fe7d2)
    * The steps are a bit different from the previous two scenarios since this one has more complex business logic. However, by reusing those existing steps, create those given, when and then steps are not that complex.
    * Again, this scenario is failed due to no implementation.
* To implement the third scenario, let's add the resume feature for the Stopwatch class first. Sounds familiar? [Git commit] (https://github.com/nerds-odd-e/stopwatch/commit/https://github.com/nerds-odd-e/stopwatch/commit/8673d519a1845852d0860b3e56416cdd6c1f6062)
    * Similarly as the second scenario, the new failing resume unit test is doing the exactly the same thing as the third scenario. (Why not?! Power of domain modeling)
    * Passing the test makes the code even more nasty now as internal status changing in a more complex way.
    * By some refactoring (with unit tests running from time to time), we make the algorithm more clean by using the total time concept. Great!!
* Finally, we make the third scenario passed by adding few lines of code into the StopwatchActivity. [Git commit] (https://github.com/nerds-odd-e/stopwatch/commit/https://github.com/nerds-odd-e/stopwatch/commit/f1e894f4a2eba664f8fa434536c36d1e0f078df5)

That's all for how we create this app in an ATDD/TDD approach. There are some possible refactoring to be done and all other commits can be ignored.
