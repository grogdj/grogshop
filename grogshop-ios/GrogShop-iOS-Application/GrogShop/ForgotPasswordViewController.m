//
//  ForgotPasswordViewController.m
//  GrogShop
//
//  Created by Nikita Pahadia on 08/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import "ForgotPasswordViewController.h"
#import "ForgotPasswordViewController.h"
#import "Constants.h"

@implementation ForgotPasswordViewController

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    self.view.backgroundColor = [UIColor whiteColor];
    self.title = @"Forgot password";

    CGRect frame = self.view.frame;
    
    float textviewWidth = frame.size.width - 50;
    
    _textfield = [[UITextField alloc] initWithFrame:CGRectMake(frame.size.width/2 - textviewWidth/2, 50 + 44,textviewWidth, 30)];
    _textfield.placeholder = @"Email address";
    _textfield.textAlignment = NSTextAlignmentCenter;
    _textfield.borderStyle = UITextBorderStyleLine;
    _textfield.delegate = self;
    [self.view addSubview:_textfield];
    
    UIButton *action = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    
    action.frame = CGRectMake(CGRectGetMidX(_textfield.frame) - 35, CGRectGetMaxY(_textfield.frame) + 20, 70, 30);
    [action setTitle:@"SEND" forState:UIControlStateNormal];
    [action addTarget:self action:@selector(sendPassword) forControlEvents:UIControlEventTouchUpInside];
    [action setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [action setBackgroundColor:[UIColor colorWithRed:134/255.0 green:212/255.0 blue:109/255.0 alpha:1.0]];
    [self.view addSubview:action];
}

- (NSString *)areTextFieldsValid {
    NSString *retStr = NULL;
    //return an alert string msg if a textfield has invalid entries
    if ([Utils isEmpty:_textfield.text]) {
        retStr = @"Email address cannot be empty!";
    } else if(![Utils isEmailAddressValid:_textfield.text]) {
        retStr = @"Please provide a valid email address.";
    }
    return retStr;
}

- (BOOL)checkFields {
    BOOL success = false;
    NSString *value = [self areTextFieldsValid];
    if (!value) { //NULL value indicates no error
        success = true;
        [_textfield resignFirstResponder];
    } else {
        [self showAlert:@"Invalid value!" message:value];
    }
    return success;
}

- (void)showAlert:(NSString *)title message:(NSString *)msg {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Invalid values!" message:msg delegate:self cancelButtonTitle:@"OK" otherButtonTitles: nil];
    [alert show];
}

- (void)sendPassword {
    if ([self checkFields]) {
        //send email
    }
}

@end
