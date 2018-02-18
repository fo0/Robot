# Robot

Simple Robot which is doing user defined actions for you. <br>
You can save your Actions configs to persist your work.

# Environment
Works on:
  - Windows
  - Linux
  
Depends:
  - Java >= 1.8

# Usage

  java -jar Robot.jar 
  
  
# Config
For Commandline Help:

    java -jar Robot.jar --help

Options:

    Options category 'config':
      --cfg [-c] (a string; default: "")
        read config

    Options category 'gui':
      --[no]gui [-g] (a boolean; default: "true")
        simple gui

# Start-Scripts

## Linux

### Examples
default


    #!/bin/bash
    java -jar Robot.jar

Load Config-File on StartUp


    #!/bin/bash
    java -jar Robot.jar --config /your/path/to/robot_cfg.robot
    
## Windows
default

    @echo off
    start javaw -jar Robot.jar --config /your/path/to/robot_cfg.robot
