//
//  InterestViewController.m
//  GrogShop
//
//  Created by Rishabh Chowdhary on 10/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import "InterestViewController.h"
#import "Constants.h"
#import "APIRequest.h"
#import "GrogCollectionCell.h"
#import "SettingsViewController.h"

@interface InterestViewController () <UICollectionViewDataSource,UICollectionViewDelegateFlowLayout> {
    BOOL isFirst;
    NSMutableArray *publicInterestsArray,*userInterestsArray;
    UICollectionView *collectionView;
    UICollectionViewFlowLayout *collectionLayout;
}


@end

@implementation InterestViewController

static NSString *reusableIdentifier = @"GrogCell";

- (instancetype)init
{
    self = [super init];
    if (self)
    {
        self.title = @"Add your Interests!";
        // self.navigationController.navigationBarHidden = YES;
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = BG_COLOR;
    isFirst = true;
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"Skip" style:UIBarButtonItemStylePlain target:self action:@selector(skipInterest)];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"Add" style:UIBarButtonItemStylePlain target:self action:@selector(addInterest)];
}
- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    if (isFirst) {
        isFirst = false;
        APIRequest *_request = [[APIRequest alloc] init];
        AppDelegate *del = [AppDelegate sharedDelegate];
        //[del startAnimating];
        [_request startGetRequestWithSuccessBlock:^(id rootObj) {
            
            [del stopAnimating];
            if (rootObj) {
                NSArray *rootArray = [NSJSONSerialization JSONObjectWithData:rootObj options: NSJSONReadingMutableContainers error:nil];
                publicInterestsArray = [[NSMutableArray alloc] init];
                userInterestsArray = [[NSMutableArray alloc] init];
                for (NSDictionary *interest in rootArray) {
                    //                        GrogCollectionCell *cell = [[GrogCollectionCell alloc] initWithFrame:CGRectMake(0, 0, 80, 60)];
                    //                        cell.title = [interest objectForKey:@"name"];
                    //                        cell.imgPath = [interest objectForKey:@"imagePath"];
                    [publicInterestsArray addObject:interest];
                }
                [self initializeCollectionView];
                
            }
        } failureBlock:^(NSError *e) {
            NSLog(@"error during interest:%@",[e localizedDescription]);
            [del stopAnimating];
            //[self showAlert:@"Logout Failed!" message:@"Please re-check values provided."];
            
        } extension:kPublicAllInterests public:true];
    }
}

- (void)skipInterest {
    [self launchSettings];
}
- (void)addInterest {
    [self launchSettings];
}
- (void)launchSettings {
    SettingsViewController *settings = [[SettingsViewController alloc] init];
    [self.navigationController pushViewController:settings animated:YES];
}

- (void)initializeCollectionView {
    collectionLayout = [[UICollectionViewFlowLayout alloc] init];
    collectionView = [[UICollectionView alloc] initWithFrame:self.view.bounds collectionViewLayout:collectionLayout];
    [collectionView registerClass:[GrogCollectionCell class] forCellWithReuseIdentifier:reusableIdentifier];
    [self.view addSubview:collectionView];
    collectionView.backgroundColor = [UIColor clearColor];
    collectionView.delegate = self, collectionView.dataSource = self;
}

- (GrogCollectionCell *)reusableCellAtIndexPath:(NSIndexPath *)indexPath {
    return [collectionView dequeueReusableCellWithReuseIdentifier:reusableIdentifier forIndexPath:indexPath];
}

#pragma mark Collection view data source methods

- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView {
    return 1;
}

- (NSInteger)collectionView:(UICollectionView *)collectionView
     numberOfItemsInSection:(NSInteger)section {
    
    return [publicInterestsArray count];
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)cv
                  cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    //NSLog(@"cell for item:%ld",(long)indexPath.row);
    GrogCollectionCell *cell = [self reusableCellAtIndexPath:indexPath];
    NSDictionary *d = [publicInterestsArray objectAtIndex:indexPath.row];
    [cell prepareWithTitle:[d objectForKey:@"name"] imagePath:[d objectForKey:@"imagePath"] selected:[userInterestsArray containsObject:indexPath]];
    return cell;
}

- (CGSize)collectionView:(UICollectionView *)cv layout:(UICollectionViewLayout*)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath  *)indexPath {
    CGRect frame = cv.frame;
    int itemsInRow = 3;
    float aspectRatio = 4.0/3.0;
    int padding = 10;
    int availableScreeWidth = frame.size.width - (padding * (itemsInRow -1));
    int width = (availableScreeWidth/itemsInRow);
    int height = (width/aspectRatio);
    return CGSizeMake(width,height);
}

// 3
//- (UIEdgeInsets)collectionView:
//(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout insetForSectionAtIndex:(NSInteger)section {
//    return UIEdgeInsetsMake(0, 2.5, 0, 2.5);
//}

#pragma mark Collection view delegate methods

- (void)collectionView:(UICollectionView *)cv didSelectItemAtIndexPath:(NSIndexPath  *)indexPath
{
    GrogCollectionCell *cell = (GrogCollectionCell *)[collectionView cellForItemAtIndexPath:indexPath];
    if (![userInterestsArray containsObject:indexPath]) {
        [userInterestsArray addObject:indexPath];
        [cell toggleHoverView:YES];
    } else {
        [userInterestsArray removeObject:indexPath];
        [cell toggleHoverView:NO];
    }
    
}

@end
