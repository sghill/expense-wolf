what
====
Java command line tool that takes a very specific Excel spreadsheet and outputs the rows relevant to me. `--help` is included

how
===
usage
-----
    usage: java -jar wolf.jar [OPTIONS] --file [absolute path to xls]
     -a,--all                 get every expense report you've submitted
     -e,--employee-id <arg>   employee id number. can be saved with -s
     -f,--file <arg>          absolute path to expense report xls
     -h,--help                print this message
     -s,--save-id             saves a file in home directory with employee id
### so then, for example:
- `java -jar wolf-1.0.jar --employee-id 10000 --save-id --file path/to/my/spreadsheet.xls`, the first time
- `java -jar wolf-1.0.jar --file path/to/my/spreadsheet.xls`, every other time

buildage
--------
1. make sure [gradle](http://gradle.org/) is installed
2. `gradle jar` builds a 'fat jar', including all dependencies (found in [build.gradle](https://github.com/sghill/expense-wolf/blob/master/build.gradle))


why
===
I'm a traveling consultant, which means lots of expense reports and little time. A giant Excel sheet is made available
periodically, but parsing through it is frustrating and tedious enough that it never happens. When a new sheet is
released, I give it to this tool and see which reports have been paid recently (default) or for all time (`--all`)

license
=======
[Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0)