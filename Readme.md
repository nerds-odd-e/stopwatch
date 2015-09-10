Here are the steps how this stopwatch app was developed in an ATDD/TDD way inspired by Steve Freeman's great book [Growing Object-Oriented Software Guided by Tests](http://www.growing-object-oriented-software.com/)

* Write the first Cucumber scenario "作为一个用户，可以看到计时" with a fake implementation. This scenario means a timer is started automatically after the app starts [Git commit] (https://github.com/nerds-odd-e/stopwatch/commit/adf03fef89486f1dd63355fc53972557f9e5b10f)
    * Create an empty Android app so that Calabash can launch it and run the scenario, no implementation for any steps yet
    * Implement the given step "用户打开应用" with no code since the app is started by Calabash before running the scenario. The scenario keep passing with ignored when and then steps
    * Implement the when step "经过(\d+)秒" with the code sleeping 1 second. The scenario keep passing with ignored then step
    * Implement the then step "看见显示(\d+)秒" with the code checking UI displaying "1s". The scenario now fails since no code implemented yet
    * Fake implementation: Add a text view to the main activity (aka. StopwatchActivity) with text "1s". This can pass the first scenario and we're sure the validation in then step works
    * Since almost all the code is pretty straightforward, there is limited refactoring we can do here.
* To pass the first Cucumber scenario with a real implementation, we need a just enough Stopwatch. [Git commit] (https://github.com/nerds-odd-e/stopwatch/commit/adf03fef89486f1dd63355fc53972557f9e5b10f)
    * The key part of implementation is a "Stopwatch" (too obvious, right? :) ), so we start with a failing unit test for this domain object. It is all about that after stopwatch just started, the secondPassed method should return 0. (too obvious too ^0^)
    * Passing the first failing test is quite easy, just return 0 in method secondPassed is enough (TDD, as simple as possible, hurray!)
    * No refactoring we can do now (first TDD cycle is done, great!)
    * The second failing test is, after stopwatch started and one second passed, secondPassed method should return 1. Sounds easy, but how we do one second passed in our unit test? Maybe you said just sleep 1 second in the test. Ok, but how we know one second passed in our code? Maybe you said we can get current system time when constructing the Stopwatch object and then get system time in the secondPassed method. Hold on!! system time is a hard dependency which we should isolate from our code. Now, let's isolate this dependency with the Clock interface and a stubClock in the test. Looks good!
    * Passing the second failing test is not a hard work, right? Once you have the test, it's easy to figure out the implementation.
    * After some refactoring, we get the code as it in Stopwatch class of this commit. And, we also take this chance to refactor the first and second unit tests with a setup for stubClock and Stopwatch.
* Now, we need the last code in the StopwatchActivity to pass the first scenario with a real implementation. [Git commit] (https://github.com/nerds-odd-e/stopwatch/commit/adf03fef89486f1dd63355fc53972557f9e5b10f)
    * We need some nasty code to enable the UI refresh timer to display the second passed (by calling Stopwatch). While the code is nasty, but it works and now the first BDD scenario works without fake implementation.
    * Shall we celebrate for the first scenario? Hold on!! We haven't clean up the nasty code yet. The code about timer, schedule and activity UI update seems to be a different responsibility which should not belong to StopwatchActivity. Let's refactor this and then we get the code of StopwatchActivity and UIRefresher of this commit. Hooray!! Long live clean code.
* What's the second scenario? A good question is raised "If 1.5 second passed, what will be displayed? 1s or 1.5s or 2s". [Git commit] (https://github.com/nerds-odd-e/stopwatch/commit/adf03fef89486f1dd63355fc53972557f9e5b10f)
    * What we finally agree is that it should display 1s when 1.5 second passed. Since we feel this case is not a key business logic, we decide that adding a unit test for it is enough.
    * So, the third failing unit test is added for this case. It's an easy work since we refactored the test code in previous step already.
    * The code passes the failing test with no change! Don't be surprised, current code of Stopwatch is simple enough to cover this case.
* What's the next step? A stopwatch needs a button to start the timer and it can't just start the timer by launching the app. [Git commit] (https://github.com/nerds-odd-e/stopwatch/commit/5d4eaef1a7f05d538ef48a5cbb8323b52c646722)
    * The given step is changed to "假如用户启动秒表" (meaning given user starts the stopwatch) for a better understanding. The step implementation is changed to tap a button (too obvious)
    * The when step is a bit trick now since we have to wait a short time for the button tap. This is obviously an issue we should address for the Calabash test ... in future
    * No change to the then step
    * Adding a button is such a small step as all code goes to StopwatchActivity without any doubt. And, that's it for this commit.

To be continued...
