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

## Byte Code format

    [ 2 byte header signature               ]
    [ 2 byte version number                 ]
    [ 4 byte source name length             ]
    [ 4 * length byte source name string    ]
    [ 1 byte nups                           ] (function 0, level 0 aka. main)
    [ 1 byte numParams                      ]
    [ 1 byte isVararg                       ]
    [ 1 byte maxStackSize                   ]
    [ 4 byte sizeCode                       ]
    [ n bytes bytecode                      ]
    ...
    [ 4 byte sizeConstants                  ]
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
    [ 4 byte sizeVariables                  ]
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
    [ 4 byte sizeFunctions                  ]
    [ 1 byte nups                           ] (function 0, level 1)
    [ 1 byte numParams                      ]
    [ 1 byte isVararg                       ]
    [ 1 byte maxStackSize                   ]
    [ 4 byte sizeCode                       ]
    [ n bytes bytecode                      ]
    ...
    [ 4 byte sizeConstants                  ]
    ...
    [ 4 byte sizeVariables                  ]
    ...
    [ 4 byte sizeFunctions                  ]
    [ 1 byte nups                           ] (function 1, level 1)
    [ 1 byte numParams                      ]
    [ 1 byte isVararg                       ]
    [ 1 byte maxStackSize                   ]
    [ 4 byte sizeCode                       ]
    [ n bytes bytecode                      ]
    ...
    [ 4 byte sizeConstants                  ]
    ...
    [ 4 byte sizeVariables                  ]
    ...

Example:

    Pos     Hex                                     Description
    ---------------------------------------------------------------------------
    0000    ca 7e                               Header magic number
    0002    02 00                               Version
    0004    0b 00 00 00                         Source name lengst 11
    0008    73 69 6d 70 6c 65 2e 6c 75 61 00    Source name string "simple.lua"
                                                ** function: [0] level 1
    0013    00                                  nups 0
    0014    00                                  numParams 0
    0015    02                                  isVararg 2
    0016    02                                  maxStackSize 2
                                                * code:
    0017    09 00 00 00                         sizeCode
    001b    02  01 00 00 00  00 00 00 00        [1] loadc 1 0  ; result
    0024    02  02 00 00 00  01 00 00 00        [2] loadc 2 1  ; incrementor
    002d    02  03 00 00 00  05 00 00 00        [3] loadc 3 5  ; end condition
    0036    02  04 00 00 00  3c 00 00 00        [4] loadc 4 3c ; jump position
    003f    11  01 00 00 00                     [5] println 1  ; jump here
    0044    03  01 00 00 00  02 00 00 00        [6] add 1 2    ; add register #1 and #2
    004d    01  00 00 00 00  10 00 00 00        [7] move 0 1   ; move value fron register #0 to #1
    0056    0d  01 00 00 00  03 00 00 00        [8] lt 1 3     ; test if value in register #1 < #3
    005f    0f  00 00 00 00  04 00 00 00        [9] test 0 4   ; if register #0 contians 0 jump to #4
                                                * constants:
    0068    02                                  sizeConstants 2
    0069    01 00 00 00                         type 1 integer
    006d    01 00 00 00                         const[0] = 1
    0070    01                                  type 1 integer
    0071    05 00 00 00                         const[1] = 5
    0075                                        * functions:
    ...

## Opcodes

    mnemonic    args    opcode  description
    -----------------------------------------------------------------------------------------------------------
    nop                 0x00    No operation.
    move        A B     0x01    Moves the value from register A to register B.
    load        A B     0x02    Load variable from slot A into register B.
    store       A B     0x03    Store variable from register A into slot B.
    loadc       A B     0x04    Load constant from slot A into register B.
    add         A B C   0x05    Add values from registers B and C and store result into register A.
    sub         A B C   0x06    Subtract values from registers B and C and store result into register A.
    mul         A B C   0x07    Multiply values from registers B and C and store result into register A.
    div         A B C   0x08    Divide values from registers B and C and store result into register A.
    mod         A B C   0x09    Modulus values from registers B and C and store result into register A.
    pow         A B C   0x0a    Powers values from registers B and C and store result into register A.
    unm         A B     0x0b    Unary minus operator.
    not         A B     0x0c    Logical not operator.
    jmp         A       0x0d    Unconditional jump.
    eq          A B C   0x0e    Compare if value in register B and C are equal and store result in register A.
    lt          A B C   0x0f    Compare if value in register B is less than the value in register C and store
                                result in register A.
    le          A B C   0x10    Compare if value in register B is less than or equal the value in register C
                                and store result in register A.
    test        A B     0x11    Boolean test, with conditional jump.
    print       A       0x12    Prints content of register.
    println     A       0x13    Prints content of register and a new line.
    return              0x14    Return from function.
    unkwonn             0xff    Unknown opcode will stop execution.
