//
//  AppRoutes.swift
//  iosApp
//
//  Created by Ashu Tyagi on 05/05/24.
//

import Foundation
import SwiftUI
import shared

enum AppRoute: Equatable {
    static func == (lhs: AppRoute, rhs: AppRoute) -> Bool {
        return lhs.key == rhs.key
    }
        
    case PlanetList
        
    case PlanetDetail(uid: String)
        
    var key: String {
        switch self {
            case .PlanetList:
                return "PlanetList"
            case .PlanetDetail:
                return "PlanetDetail"
            }
        }
}

extension AppRoute {
    @ViewBuilder
    func getView() -> some View {
        switch self {
        case.PlanetList:
            PlanetListScreen()
        case .PlanetDetail(uid: let uid):
            PlanetDetailScreen(uid: uid)
        }
    }
}
