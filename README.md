# Robot

Simple Robot which is doing user defined actions for you. <br>
You can load and save your actions via configs to persist your work.

# Actions

* Commandline
* SSH
* SCP
* FTP
* Simple Download (SRC|DST)
* Zip / Unzip


TODO:

* TAR
* Interactive Console (CLI)

# Demo
![Demo-GUI](https://github.com/fo0/Robot/blob/master/Robot/files/demo-gui-v0-7-3.gif)

# Environment
Works on:
  - Windows [Untest]
  - Linux
  
Depends:
  - Java >= 1.8

# Usage

  java -jar Robot.jar 
  
  
# Config
For Commandline Help:

    java -jar Robot.jar --help

Options:

    Options category 'Config':
      --config [-c] (a string; default: "")
        Read config by Path

    Options category 'Errors':
      --[no]ignoreErrors [-i] (a boolean; default: "false")
        Ignore Errors in Chain

    Options category 'GUI':
      --[no]nogui [-n] (a boolean; default: "false")
        Graphical User Interface

    Options category 'Verbose':
      --[no]verbose [-v] (a boolean; default: "false")
        Debug Log Level

# Start-Scripts

## Linux

### Examples
default


    #!/bin/bash
    java -jar Robot.jar

Load Config-File on StartUp


    #!/bin/bash
    java -jar Robot.jar --config /your/path/to/robot_cfg.robot

Using on CLI and Load Config-File on StartUp


    #!/bin/bash
    java -jar Robot.jar --nogui --config /your/path/to/robot_cfg.robot
    
## Windows
default

    @echo off
    start javaw -jar Robot.jar --config /your/path/to/robot_cfg.robot
