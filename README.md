# Register Machine

## Big picture

    Assembly Code
         |
         v
    +-----------+
    | Assembler |
    +-----------+
         |
         v
     Byte Code
         |
         v
    +-----------+
    |    VM     |
    +-----------+

## ASM Syntax

move    #1 #2       ; move content of reg 1 to reg 2
add     #1 #1       ; add reg 1 and reg 2 into reg 0
add     #1 3        ; add constant 3 and reg 1 into reg 0
jmp     3           ; jump to instruction 3
jmp     #3          ; jump to instruction position stored in reg 3

loadc   #3 1234     ; load constant integer into reg 3
loadc   #3 12.34    ; load constant float into reg 3
loadc   #3 nil      ; load constant nil into reg 3
loadc   #3 true     ; load constant true into reg 3
loadc   #3 false    ; load constant false into reg 3
loadc   #3 "string" ; load constant string into reg 3

## Byte Code format

    [ 2 byte header signature               ]
    [ 2 byte version number                 ]
    [ 4 byte source name length             ]
    [ 4 * length byte source name string    ]
    [ 1 byte nups                           ] (function 0, level 0 aka. main)
    [ 1 byte numParams                      ]
    [ 1 byte isVararg                       ]
    [ 1 byte maxStackSize                   ]
    [ 1 byte sizeCode                       ]
    [ n bytes bytecode                      ]
    ...
    [ 1 byte sizeConstants                  ]
    [ 1 byte type (integer)                 ]
    [ 4 byte integer                        ]
    [ 1 byte type (float)                   ]
    [ 4 byte float                          ]
    [ 1 byte type (boolean)                 ]
    [ 1 byte boolean                        ]
    [ 1 byte type (string)                  ]
    [ 4 byte stringLength                   ]
    [ 4 * n byte string                     ]
    ...
    [ 1 byte sizeVariables                  ]
    [ 1 byte type (integer)                 ]
    [ 4 byte integer                        ]
    [ 1 byte type (float)                   ]
    [ 4 byte float                          ]
    [ 1 byte type (boolean)                 ]
    [ 1 byte boolean                        ]
    [ 1 byte type (string)                  ]
    [ 4 byte stringLength                   ]
    [ 4 * n byte string                     ]
    ...
    [ 1 byte sizeFunctions                  ] 
    [ 1 byte nups                           ] (function 0, level 1)
    [ 1 byte numParams                      ]
    [ 1 byte isVararg                       ]
    [ 1 byte maxStackSize                   ]
    [ 1 byte sizeCode                       ]
    [ n bytes bytecode                      ]
    ...
    [ 1 byte sizeConstants                  ]
    ...
    [ 1 byte sizeVariables                  ]
    ...
    [ 1 byte sizeFunctions                  ] 
    [ 1 byte nups                           ] (function 1, level 1)
    [ 1 byte numParams                      ]
    [ 1 byte isVararg                       ]
    [ 1 byte maxStackSize                   ]
    [ 1 byte sizeCode                       ]
    [ n bytes bytecode                      ]
    ...
    [ 1 byte sizeConstants                  ]
    ...
    [ 1 byte sizeVariables                  ]
    ...

Example:

    Pos     Hex                                 Description
    ---------------------------------------------------------------------------
    0000    ca  7e                              Header signature
    0002    02  00                              Version
    0004    0b  00 00 00                        Source name lengst 11
    0008    73  69 6d 70 6c  65 2e 6c 75 61 00  Source name string "simple.lua"
                                                ** function: [0] level 1
    0013    00                                  nups 0
    0014    00                                  numParams 0
    0015    02                                  isVararg 2
    0016    02                                  maxStackSize 2
                                                * code:
    0017    09                                  sizeCode 
    0018    02  01 00 00 00  00 00 00 00        [1] loadc 1 0  ; result
    0021    02  02 00 00 00  01 00 00 00        [2] loadc 2 1  ; incrementor
    002a    02  03 00 00 00  05 00 00 00        [3] loadc 3 5  ; end condition
    0033    02  04 00 00 00  3c 00 00 00        [4] loadc 4 3c ; jump position
    003c    11  01 00 00 00                     [5] println 1  ; jump here
    0041    03  01 00 00 00  02 00 00 00        [6] add 1 2    ; add register #1 and #2
    004a    01  00 00 00 00  10 00 00 00        [7] move 0 1   ; move value fron register #0 to #1
    0053    0d  01 00 00 00  03 00 00 00        [8] lt 1 3     ; test if value in register #1 < #3
    005c    0f  00 00 00 00  04 00 00 00        [9] test 0 4   ; if register #0 contians 0 jump to #4
    0065                                        * constants:
    0065    02                                  sizeConstants 2
    0066    01                                  type 1 integer
    0067    01 00 00 00                         const[0] = 1
    006b    01                                  type 1 integer
    006c    05 00 00 00                         const[1] = 5
    0070                                        * functions:

## Opcodes

    mnemonic args   byte
    --------------------
    move     A B    0x01
    loadc    A B    0x02
    add      A B    0x03
    sub      A B    0x04
    mul      A B    0x05
    div      A B    0x06
    mod      A B    0x07
    pow      A B    0x08
    unm      A      0x09
    not      A      0x0a
    jmp      A      0x0b
    eq       A B    0x0c
    lt       A B    0x0d
    le       A B    0x0e
    test     A B    0x0f
    print    A      0x10
    println  A      0x11
    unkwonn         0xff
