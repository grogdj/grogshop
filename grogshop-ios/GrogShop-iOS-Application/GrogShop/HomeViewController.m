//
//  HomeViewController.m
//  GrogShop
//
//  Created by Nikita Pahadia on 07/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import "HomeViewController.h"
#import "Constants.h"
#import "APIRequest.h"
#import "GrogCollectionCell.h"

@interface HomeViewController () <UICollectionViewDataSource,UICollectionViewDelegateFlowLayout> {
    BOOL isFirst;
    NSMutableArray *publicClubsArray,*userInterestsArray,*userPublicClubInterestArray;
    UICollectionView *collectionView;
    UICollectionViewFlowLayout *collectionLayout;
}


@end

@implementation HomeViewController

- (instancetype)init
{
    self = [super init];
    if (self)
    {
        [self.tabBarItem setTitle:@"Home"];
        self.title = @"Home";
       // self.navigationController.navigationBarHidden = YES;
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = BG_COLOR;
    isFirst = true;
}

- (void)compareUserInterestWithPublicClubs {
    userPublicClubInterestArray = [[NSMutableArray alloc] init];
    for (NSDictionary *club in publicClubsArray) {
        NSString *i = [club objectForKey:@"interest"];
        for (NSDictionary *interest in userInterestsArray) {
            NSString *n = [interest objectForKey:@"name"];
            if ([i isEqualToString:n]) {
               // NSLog(@"club:%@",[club description]);
                //NSLog(@"interest:%@",[interest description]);
                [userPublicClubInterestArray addObject:club];
            }
        }
    }
    [userInterestsArray removeAllObjects]; userInterestsArray = nil;
    [publicClubsArray removeAllObjects]; publicClubsArray = nil;
    dispatch_async(dispatch_get_main_queue(), ^{
        [self initializeCollectionView];
    });
    
}

- (void)fetchUserInterests {
    //users/62/interests
    
    APIRequest *request = [[APIRequest alloc] init];
    AppDelegate *del = [AppDelegate sharedDelegate];
    
    [request startGetRequestWithSuccessBlock:^(id rootObj) {
        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
            if (rootObj) {
                NSArray *rootArray = [NSJSONSerialization JSONObjectWithData:rootObj options: NSJSONReadingMutableContainers error:nil];
                for (NSDictionary *interest in rootArray) {
                    NSLog(@"%@",interest);
                    [userInterestsArray addObject:interest];
                }
                [self compareUserInterestWithPublicClubs];
            }
        });
        [del stopAnimating];
    } failureBlock:^(NSError *e) {
        [del stopAnimating];
    } extension:[NSString stringWithFormat:@"/users/%ld/interests",del.userId] public:false];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    if (isFirst) {
        isFirst = false;
        if (_firstLogin) {
            APIRequest *_request = [[APIRequest alloc] init];
            AppDelegate *del = [AppDelegate sharedDelegate];
            [del startAnimating];
            [_request startGetRequestWithSuccessBlock:^(id rootObj) {
                
                if (rootObj) {
                    NSArray *rootArray = [NSJSONSerialization JSONObjectWithData:rootObj options: NSJSONReadingMutableContainers error:nil];
                    publicClubsArray = [[NSMutableArray alloc] init];
                    userInterestsArray = [[NSMutableArray alloc] init];
                    for (NSDictionary *club in rootArray) {
                        //"description", "founderEmail", id, "image", "interest", "name"
                        [publicClubsArray addObject:club];
                        //NSLog(@"%@",[interest description]);
                        
                    }
                    [self fetchUserInterests];
                    //[self initializeCollectionView];
                }
            } failureBlock:^(NSError *e) {
                NSLog(@"error during public club:%@",[e localizedDescription]);
                [del stopAnimating];
                //[self showAlert:@"Logout Failed!" message:@"Please re-check values provided."];
                
            } extension:kAllClubs public:false];
        }
    }
}

- (void)skipInterest {
    
}
- (void)addInterest {
    
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

    return [userPublicClubInterestArray count];
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)cv
                  cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    //NSLog(@"cell for item:%ld",(long)indexPath.row);
    GrogCollectionCell *cell = [self reusableCellAtIndexPath:indexPath];
    NSDictionary *d = [userPublicClubInterestArray objectAtIndex:indexPath.row];
    [cell prepareWithTitle:[d objectForKey:@"name"] imagePath:[d objectForKey:@"image"] selected:false];
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
    
}

@end
