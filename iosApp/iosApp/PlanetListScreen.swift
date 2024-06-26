//
//  PlanetListScreen.swift
//  iosApp
//
//  Created by Ashu Tyagi on 05/05/24.
//

import Foundation
import SwiftUI
import FlowStacks
import shared

struct PlanetListScreen: View {
    
    @EnvironmentObject private var navigator: FlowNavigator<AppRoute>
    
    private var pagingHelper = SwiftUiPagingHelper<Planet>()
    
    @State private var viewModel : PlanetListViewModel? = nil
    
    @State private var hasNextPage: Bool = false
    
    @State private var errorMessage: String? = nil
    
    @State private var showLoadingPlaceholder: Bool = false
    
    @State private var items: Set<Planet> = []
    
    var body: some View {
        VStack(alignment: .center, spacing: 50) {
            List {
                ForEach(items.sorted(by: { first, second in
                    Int(first.uid) ?? 0 < Int(second.uid) ?? 0
                }), id: \.uid) { item in
                    let text = "\(item.uid) - \(item.name)"
                    Text(text)
                        .font(.system(size:30, weight: .semibold))
                        .italic()
                        .foregroundColor(.white)
                        .padding(.all, 16)
                        .listRowInsets(EdgeInsets())
                        .listRowBackground(Color.clear)
                        .onTapGesture {
                            navigator.push(.PlanetDetail(uid: item.uid))
                        }
                }
                
                if(showLoadingPlaceholder) {
                    getLoadindView()
                }
                
                if(hasNextPage && errorMessage == nil && !items.isEmpty) {
                    getLoadindView()
                        .onAppear{
                            pagingHelper.loadNextPage()
                        }
                }
                if(errorMessage != nil) {
                    getErrorView(
                        errorMessage: errorMessage!,
                        onRetryClicked: {
                            pagingHelper.retry()
                            self.errorMessage = nil
                        }
                    )
                }
            }
            .listStyle(.plain)
            
            if(items.isEmpty){
                Spacer()
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color.black.opacity(0.92)).edgesIgnoringSafeArea(.bottom)
        .task {
            let viewModel = SharedModuleDependencies.shared.planetListViewModel
            await withTaskCancellationHandler(
                operation: {
                    self.viewModel = viewModel
                    for await pagingData in viewModel.pager {
                        try? await skie(pagingHelper).submitData(pagingData: pagingData)
                    }
                },
                onCancel: {
                    viewModel.onCleared()
                    DispatchQueue.main.async {
                        self.viewModel = nil
                    }
                }
            )
        }
        .task {
            for await _ in pagingHelper.onPagesUpdatedFlow {
                pagingHelper.getItems().forEach { item in
                    self.items.insert(item)
                }
            }
        }
        .task {
            for await loadState in pagingHelper.loadStateFlow {
                switch onEnum(of: loadState.append) {
                case .error(let errorState):
                    self.errorMessage = errorState.error.message
                    break
                case .loading(_):
                    break
                case .notLoading(let notLoadingState):
                    self.hasNextPage = !notLoadingState.endOfPaginationReached
                    break
                }
                
                switch onEnum(of: loadState.refresh) {
                case .error(let errorState):
                    self.errorMessage = errorState.error.message
                    self.showLoadingPlaceholder = false
                    break
                case .loading(_):
                    self.showLoadingPlaceholder = true
                    break
                case .notLoading(_):
                    self.showLoadingPlaceholder = false
                    break
                }
            }
        }
    }
    
    @ViewBuilder
    private func getLoadindView() -> some View {
        HStack {
            ListLoadingItem()
        }
        .listRowInsets(EdgeInsets())
        .listRowSeparator(.hidden, edges: .all)
        .listRowBackground(Color.clear)
    }
    
    @ViewBuilder
    private func getErrorView(
        errorMessage: String,
        onRetryClicked: @escaping () -> Void
    ) -> some View {
        HStack {
            ListErrorItem(
                errorMessage: errorMessage,
                onRetryClicked: onRetryClicked
            )
        }
        .listRowInsets(EdgeInsets())
        .listRowSeparator(.hidden, edges: .all)
        .listRowBackground(Color.clear)
    }
    
}
