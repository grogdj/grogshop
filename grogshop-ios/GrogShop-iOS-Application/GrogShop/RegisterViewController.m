//
//  RegisterViewController.m
//  GrogShop
//
//  Created by Nikita Pahadia on 08/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import "RegisterViewController.h"
#import "Constants.h"
#import "APIRequest.h"

@implementation RegisterViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = BG_COLOR;
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    self.title = @"Create an account";
    
    CGRect frame = self.view.frame;
    
    float textviewWidth = frame.size.width - 50;
    
    _email = [[UITextField alloc] initWithFrame:CGRectMake(frame.size.width/2 - textviewWidth/2, 70 + 44,textviewWidth, 30)];
    _email.placeholder = @"Email id";
    _email.textAlignment = NSTextAlignmentCenter;
    _email.borderStyle = UITextBorderStyleLine;
    _email.backgroundColor = [UIColor whiteColor];
    _email.delegate = self;
    [self.view addSubview:_email];
    
    _password = [[UITextField alloc] initWithFrame:CGRectMake(frame.size.width/2 - textviewWidth/2, CGRectGetMaxY(_email.frame) + 20,textviewWidth, _email.frame.size.height)];
    _password.placeholder = @"Password";
    _password.secureTextEntry = YES;
    _password.textAlignment = NSTextAlignmentCenter;
    _password.borderStyle = UITextBorderStyleLine;
    _password.backgroundColor = [UIColor whiteColor];
    _password.delegate = self;
    [self.view addSubview:_password];
    
    _repeatp = [[UITextField alloc] initWithFrame:CGRectMake(frame.size.width/2 - textviewWidth/2, CGRectGetMaxY(_password.frame) + 20,textviewWidth, _email.frame.size.height)];
    _repeatp.placeholder = @"Repeat Password";
    _repeatp.secureTextEntry = YES;
    _repeatp.textAlignment = NSTextAlignmentCenter;
    _repeatp.borderStyle = UITextBorderStyleLine;
    _repeatp.backgroundColor = [UIColor whiteColor];
    _repeatp.delegate = self;
    [self.view addSubview:_repeatp];
    
    self.registerUser = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    _registerUser.frame = CGRectMake(_repeatp.frame.origin.x, CGRectGetMaxY(_repeatp.frame) + 20, 80, 30);
    [_registerUser setTitle:@"Sign up" forState:UIControlStateNormal];
    [_registerUser addTarget:self action:@selector(startRegistration) forControlEvents:UIControlEventTouchUpInside];
    [_registerUser setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [_registerUser setBackgroundColor:[UIColor colorWithRed:123/255.0 green:169/255.0 blue:208/255.0 alpha:1.0]];
    [self.view addSubview:_registerUser];
    
}

- (NSString *)areTextFieldsValid {
    NSString *retStr = NULL;
    //return an alert string msg if a textfield has invalid entries
    if ([Utils isEmpty:_email.text]) {
        retStr = @"Email address cannot be empty!";
    } else if([Utils isEmpty:_password.text]) {
        retStr = @"Password field cannot be empty!";
    } else if([_password.text length] < 6) {
        retStr = @"Password must be of atleast 6 characters.";
    } else if(![Utils isEmailAddressValid:_email.text]) {
        retStr = @"Please provide a valid email address.";
    } else if(![_password.text isEqualToString: _repeatp.text]) {
        retStr = @"Passwords do not match.";
    }
    return retStr;
}

- (BOOL)checkFields {
    BOOL success = false;
    NSString *value = [self areTextFieldsValid];
    if (!value) { //NULL value indicates no error
        success = true;
        [_email resignFirstResponder];
        [_password resignFirstResponder];
    } else {
        [self showAlert:@"Invalid value!" message:value];
    }
    return success;
}

- (void)startRegistration {
    if ([self checkFields]) {
        AppDelegate *del = [AppDelegate sharedDelegate];
        [del startAnimating];
        APIRequest *_request = [[APIRequest alloc] init];
        [_request startRegistrationRequestWithSuccessBlock:^(id rootObj) {
            NSLog(@"success for registration");
            [del stopAnimating];
            NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
            [defaults setObject:_email.text forKey:User_email];
            [defaults synchronize];
            [self.navigationController popViewControllerAnimated:YES];
        } failureBlock:^(NSError *e) {
            NSLog(@"error during registration:%@",[e localizedDescription]);
            [del stopAnimating];
            [self showAlert:@"Registration Failed!" message:@"Please re-check values provided."];
            
        } email:_email.text password:_password.text];
    }
}

- (void)showAlert:(NSString *)title message:(NSString *)msg {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Invalid values!" message:msg delegate:self cancelButtonTitle:@"OK" otherButtonTitles: nil];
    [alert show];
}

@end
